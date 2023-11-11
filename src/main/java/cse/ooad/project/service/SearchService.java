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
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

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

    public List<Student> searchStudents(Long gender, Time awakeTime, Time sleepTime, Long type,
        String intro) {
        return studentRepository.getStudentsBySleepTimeLessThanAndAwakeTimeGreaterThanAndIntroLikeAndGenderAndType(
            sleepTime, awakeTime, intro, gender, type);
    }

    public List<Student> searchStudents(){
        return studentRepository.findAll();
    }




    public Set<Group> searchGroups(Long gender, Time awakeTime, Time sleepTime,
        Long type, String intro) {
        List<Student> students = studentRepository.getStudentsBySleepTimeLessThanAndAwakeTimeGreaterThanAndIntroLikeAndGenderAndType(
            sleepTime, awakeTime, intro, gender, type);
        Set<Group> groupSet = new HashSet<>();
        students.forEach(t -> groupSet.add( t.getGroup()));
        return groupSet;
    }

    public List<Region> searchRegion() {
        return regionRepository.findAll();
    }

    public List<Building> searchBuilding(Long id) {
        return buildingRepository.getBuildingsByRegionId(id);
    }

    public List<Floor> searchFloor(Long id) {
        return floorRepository.getFloorsByBuildingId(id);
    }

    public List<Room> searchRoom(Long id) {
        return roomRepository.getRoomsByFloorId(id);
    }

    public List<Group> searchAllGroup(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return groupRepository.findAll(pageable).getContent();
    }


    public List<Student> searchStudentByName(String name) {
        return studentRepository.getStudentsByName(name);
    }

    public Student searchStudentById(Long id){
        return studentRepository.getStudentByStudentId(id);
    }

    public Group searchGroupById(Long id){
        return groupRepository.getGroupByGroupId(id);
    }

    public Region searchRegionById(Long id){
        return regionRepository.findById(id).orElse(null);
    }

    public Msg searchMsgById(Long id){
        return msgRepository.findById(id).orElse(null);
    }

    public Room searchRoomById(Long id){
        return roomRepository.findById(id).orElse(null);
    }

    public Teacher searchTeacherById(Long id){
        return teacherRepository.findById(id).orElse(null);
    }

    public Floor searchFloorById(Long id){
        return floorRepository.findById(id).orElse(null);
    }

    public Building searchBuildingById(Long id){
        return buildingRepository.findById(id).orElse(null);
    }

    public Comment searchCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public Timeline searchTimelineById(Long id){
        return timelineRepository.findById(id).orElse(null);
    }



}
