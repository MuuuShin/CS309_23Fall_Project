package cse.ooad.project.service;


import cse.ooad.project.model.*;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.FloorRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.MsgRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;
import cse.ooad.project.repository.TimelineRepository;
import cse.ooad.project.utils.StudentTimeMatch;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchService {


    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private FloorRepository floorRepository;
    @Autowired
    private MsgRepository msgRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TimelineRepository timelineRepository;


    /**
     * 搜索学生，先根据性别、类型、介绍进行筛选，然后根据早起时间、睡觉时间的重合度进行排序返回
     * @param gender 0为男，1为女，2为不限
     * @param awakeTime 早起时间
     * @param sleepTime 睡觉时间
     * @param type 枚举
     * @param intro 介绍
     * @return 返回学生列表
     */

    public List<Student> searchStudents(Long gender, Time awakeTime, Time sleepTime, Long type,
        String intro) {
        //首先获取所有的符合介绍匹配的上、类型相同的学生
        List<Student> students = studentRepository.getStudentsByIntroContainingAndGenderAndType(intro, gender, type);
        //然后调用StudentTimeMatch内的方法获取匹配程度排序返还要求的学生
        students = students.stream().filter(t -> t.getAwakeTime() != null && t.getSleepTime() != null).
            filter(t ->
            StudentTimeMatch.TimeMatch(t.getAwakeTime(), t.getSleepTime(), awakeTime, sleepTime) != 0).
            sorted((o1, o2) -> {
                long o1Long = StudentTimeMatch.TimeMatch(o1.getAwakeTime(), o1.getSleepTime(), awakeTime, sleepTime);
                long o2Long = StudentTimeMatch.TimeMatch(o2.getAwakeTime(), o2.getSleepTime(), awakeTime, sleepTime);
            return Long.compare(o1Long, o2Long);
            }).collect(Collectors.toList());
        return students;
    }



    /**
     * 搜索队伍，先根据性别、类型、介绍进行筛选，然后根据早起时间、睡觉时间的重合度进行排序返回，实际是是调用了searchStudents方法
     * @param gender 0为男，1为女，2为不限
     * @param awakeTime 早起时间
     * @param sleepTime 睡觉时间
     * @param type 学生的枚举
     * @param intro 介绍
     * @return 返回队伍列表, 只要队伍里有人符合要求就返回，不会返回满4人的
     */
    @Transactional
    public List<Group> searchGroups(Long gender, Time awakeTime, Time sleepTime,
        Long type, String intro) {
        List<Student> students = searchStudents(gender, awakeTime, sleepTime, type, intro);
        System.out.println(students.size());
        List<Group> groupList = new ArrayList<>();
        students.forEach(t -> {
            if (t.getGroup() != null&&!groupList.contains(t.getGroup())&&t.getGroup().getMemberList().size()<4){
                groupList.add(t.getGroup());
            }});
        //选出名字或者介绍中包含关键字的队伍
        List<Group> containIntro = groupList.stream().filter(t -> t.getIntro().contains(intro) || t.getName().contains(intro)).toList();
        List<Group> noContainIntro = groupList.stream().filter(t -> !t.getIntro().contains(intro) && !t.getName().contains(intro)).toList();
        containIntro = getGroupsSortByTime(awakeTime, sleepTime, containIntro);
        noContainIntro = getGroupsSortByTime(awakeTime, sleepTime, noContainIntro);
        ArrayList<Group> groups = new ArrayList<>(containIntro);
        groups.addAll(noContainIntro);
        return groups;
    }

    private List<Group> getGroupsSortByTime(Time awakeTime, Time sleepTime,
        List<Group> containIntro) {
        containIntro = containIntro.stream().sorted((o1, o2) -> {
            long o1Long;
            long o2Long;
            o1Long = o1.getMemberList().stream().mapToLong(t -> StudentTimeMatch.TimeMatch(t.getAwakeTime(), t.getSleepTime(), awakeTime, sleepTime)).sum()/o1.getMemberList().size();
            o2Long = o2.getMemberList().stream().mapToLong(t -> StudentTimeMatch.TimeMatch(t.getAwakeTime(), t.getSleepTime(), awakeTime, sleepTime)).sum()/o2.getMemberList().size();
            return Long.compare(o2Long, o1Long);
        }).toList();
        return containIntro;
    }

    public List<Region> searchAllRegion() {
        return regionRepository.findAll();
    }

    public List<Building> searchBuildingByRegion(Long id) {
        return buildingRepository.getBuildingsByRegionId(id);
    }

    public List<Floor> searchFloorByFloor(Long id) {
        return floorRepository.getFloorsByBuildingId(id);
    }

    public List<Room> searchRoomByFloor(Long id) {
        return roomRepository.getRoomsByFloorId(id);
    }

    public List<Room> searchRoomByBuildingId(Long id) {
        List<Floor> floors = floorRepository.getFloorsByBuildingId(id);
        List<Room> rooms = new ArrayList<>();
        floors.forEach(t -> rooms.addAll(roomRepository.getRoomsByFloorId(t.getFloorId())));
        return rooms;
    }

    public List<Room> searchRoomByRegionId(Long id) {
        List<Building> buildings = buildingRepository.getBuildingsByRegionId(id);
        List<Room> rooms = new ArrayList<>();
        buildings.forEach(t -> rooms.addAll(searchRoomByBuildingId(t.getBuildingId())));
        return rooms;
    }



    public List<Group> searchAllGroup(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return groupRepository.findAll(pageable).getContent();
    }


    public List<Student> searchStudentByName(String name) {
        return studentRepository.getStudentsByName(name);
    }

    public Student searchStudentByStudentId(Long id){
        return studentRepository.getStudentByStudentId(id);
    }

    public Group searchGroupByGroupId(Long id){
        return groupRepository.getGroupByGroupId(id);
    }

    public Region searchRegionByRegionId(Long id){
        return regionRepository.findById(id).orElse(null);
    }

    public Msg searchMsgByMsgId(Long id){
        return msgRepository.findById(id).orElse(null);
    }

    public Room searchRoomByRoomId(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    public Teacher searchTeacherByTeacherId(Long id){
        return teacherRepository.findById(id).orElse(null);
    }

    public Floor searchFloorByFloorId(Long id){
        return floorRepository.findById(id).orElse(null);
    }

    public Building searchBuildingByBuildingId(Long id){
        return buildingRepository.findById(id).orElse(null);
    }

    public Comment searchCommentByCommentId(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public Timeline searchTimelineById(Long id){
        return timelineRepository.findById(id).orElse(null);
    }



}
