package cse.ooad.project.utils;

import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Timeline;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TimelineRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DynamicTask {

    private final static Logger logger = LoggerFactory.getLogger(DynamicTask.class);

    // 线程存储器
    public static ConcurrentHashMap<String, ScheduledFuture> map = new ConcurrentHashMap<>();
    List<ScheduledFuture> list = Collections.synchronizedList(new ArrayList<>());
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    TimelineRepository timelineRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void startCron() {
        Collection<Timeline> timelines = timelineRepository.findAll();
        timelines.forEach(t -> list.add(threadPoolTaskScheduler.schedule(new myTask(t.getType()), new CronTrigger(getCron(t.getEndTime3())))));
        System.out.println(Thread.currentThread().getName());

    }

    public void update() {
        list.forEach(t -> t.cancel(true));
        list.clear();
        startCron();
    }

    /**
     * 日期转化为cron表达式
     * @param date
     * @return
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ?";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    private class myTask implements Runnable {
        private Integer name;



        myTask(Integer name) {
            this.name = name;
        }

        @Override
        @Transactional
        public void run() {
            //todo 把人都random进去
            System.out.println("test" + name);
            List<Student> students = studentRepository.findAll();
            List<Group> groups = groupRepository.findAll();
            List<Room> rooms = roomRepository.findAll();
            logger.info(groups.size() + "");
            groups.forEach(t -> {
                List<Student> m = t.getMemberList();
                if (t.getRoomId() == null){
                    m.forEach(e -> e.setGroupId(null));
                    studentRepository.saveAll(m);
                    groupRepository.delete(t);
                }
            });
            logger.info(groups.size() + "");
            //已经住人的room的id
            List<Long> roomId = groups.stream().map(Group::getRoomId).toList();
            //根据学生type分成4个list
            List<Student> type1 = students.stream().filter(s -> s.getType() == StudentType.MASTER_MALE.type).toList();
            List<Student> type2 = students.stream().filter(s -> s.getType() == StudentType.MASTER_FEMALE.type).toList();
            List<Student> type3 = students.stream().filter(s -> s.getType() == StudentType.DOCTOR_MALE.type).toList();
            List<Student> type4 = students.stream().filter(s -> s.getType() == StudentType.DOCTOR_FEMALE.type).toList();
            //把队伍按leadertype分成4个list
            List<Group> groups1 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.MASTER_MALE.type).toList();
            List<Group> groups2 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.MASTER_FEMALE.type).toList();
            List<Group> groups3 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.DOCTOR_MALE.type).toList();
            List<Group> groups4 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.DOCTOR_FEMALE.type).toList();

            logger.info("DynamicTask.myTask.run 定时任务执行中，任务名：{}", name);
        }
    }



}