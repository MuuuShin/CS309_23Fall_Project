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

/**
 * 动态定时任务, 用于阶段三的时候，随机把房间分配给队伍

 */
@Component
@Transactional
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

    /**
     * 开启定时任务, 从数据库获取所有timelines
     */
    public void startCron() {
        Collection<Timeline> timelines = timelineRepository.findAll();
        timelines.forEach(t -> list.add(threadPoolTaskScheduler.schedule(new RandomInsertRoomInStage3Task(t.getType()), new CronTrigger(getCron(t.getEndTime3())))));
        System.out.println(Thread.currentThread().getName());

    }

    /**
     * 更新定时用户
     */
    public void update() {
        list.forEach(t -> t.cancel(true));
        list.clear();
        startCron();
    }

    /**
     * 日期转化为cron表达式
     * @param date 日期
     * @return cron表达式
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ?";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    private class RandomInsertRoomInStage3Task implements Runnable {
        private final Integer name;



        RandomInsertRoomInStage3Task(Integer name) {
            this.name = name;
        }

        /**
         * 任务执行体, 阶段三的时候，随机把房间分配给队伍
         */
        @Override
        @Transactional
        public void run() {
            System.out.println("test" + name);
            List<Student> students = studentRepository.findAll();
            List<Group> groups = groupRepository.findAllWithMembers();
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
            //找出所有没有被选的房间
            List<Room> roomsWithoutGroup = new ArrayList<>(rooms);
            roomsWithoutGroup.removeAll(groups.stream().map(Group::getRoom).toList());
            //根据学生type分成4个list
            List<Student> type1 = students.stream().filter(s -> s.getType() == StudentType.MASTER_MALE.type).toList();
            List<Student> type2 = students.stream().filter(s -> s.getType() == StudentType.MASTER_FEMALE.type).toList();
            List<Student> type3 = students.stream().filter(s -> s.getType() == StudentType.DOCTOR_MALE.type).toList();
            List<Student> type4 = students.stream().filter(s -> s.getType() == StudentType.DOCTOR_FEMALE.type).toList();
            List<List<Student>> studentTypeLists = new ArrayList<>();
            studentTypeLists.add(type1);
            studentTypeLists.add(type2);
            studentTypeLists.add(type3);
            studentTypeLists.add(type4);
            //把队伍按leadertype分成4个list
            List<Group> groups1 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.MASTER_MALE.type).toList();
            List<Group> groups2 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.MASTER_FEMALE.type).toList();
            List<Group> groups3 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.DOCTOR_MALE.type).toList();
            List<Group> groups4 = groups.stream().filter(t -> t.getMemberList().get(0).getType() == StudentType.DOCTOR_FEMALE.type).toList();
            List<List<Group>> groupTypeLists = new ArrayList<>();
            groupTypeLists.add(groups1);
            groupTypeLists.add(groups2);
            groupTypeLists.add(groups3);
            groupTypeLists.add(groups4);
            for(int i = 0; i < 4; i++){
                int studentType = i + 1;
                List<Group> tempGroups = groupTypeLists.get(i);
                //找出所有没有入队的学生
                List<Student> tempStudents = new ArrayList<>(studentTypeLists.get(i).stream().filter(s -> s.getGroupId() == null).toList());
                tempGroups.forEach(t -> {
                    //如果队伍里的人数不够，就把没有入队的人加进去
                    Room room = t.getRoom();
                    int size = RoomType.valueOf(room.getType()).getCapacity();
                    while (t.getMemberList().size() < size && tempStudents.size() > 0){
                        Student s = tempStudents.get(0);
                        s.setGroupId(t.getGroupId());
                        tempStudents.remove(0);
                    }
                });
                //如果还有剩下的学生，就创建新的队伍，先根据剩下的房间创建队伍
                List<Room> tempRooms = new ArrayList<>(roomsWithoutGroup.stream().filter(r -> RoomType.valueOf(r.getType()).getStudentType() == studentType).toList()) ;
                while (tempStudents.size() > 0){
                    if (tempRooms.size() == 0){
                        logger.info("DynamicTask.myTask.run 没有剩余的房间了");
                        break;
                    }
                    Room room = tempRooms.get(0);
                    tempRooms.remove(0);
                    int roomSize = RoomType.valueOf(room.getType()).getCapacity();
                    List<Student> newGroupMembers = new ArrayList<>();
                    for(int j = 0; j < roomSize;j++){
                        if (tempStudents.size() == 0){
                            logger.info("DynamicTask.myTask.run 没有剩余的学生了");
                            break;
                        }
                        newGroupMembers.add(tempStudents.get(0));
                        studentRepository.save(tempStudents.get(0));
                        tempStudents.remove(0);
                    }
                    Group newGroup = new Group();
                    //newGroup.setMemberList(newGroupMembers);
                    newGroup.setRoomId(room.getRoomId());
                    newGroup.setLeader(newGroupMembers.get(0).getStudentId());
                    newGroup.setName(newGroupMembers.get(0).getName() + "的队伍");
                    Group group = groupRepository.save(newGroup);
                    newGroupMembers.forEach(s -> s.setGroupId(group.getGroupId()));

                }
            }
            //保存所有学生，更新数据库
            studentRepository.saveAll(students);

            logger.info("DynamicTask.myTask.run 定时任务执行中，任务名：{}", name);
        }
    }



}