package cse.ooad.project;

import cse.ooad.project.model.Building;
import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Msg;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Timeline;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.service.MsgService;
import cse.ooad.project.service.RoomService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.service.TimelineService;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Cs30923FallProjectApplicationTests {

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    LoginService loginService;

    @Autowired
    MsgService msgService;


    @Autowired
    RoomService roomService;

    @Autowired
    SearchService searchService;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TimelineService timelineService;
    @Autowired
    private CommentRepository commentRepository;


    @Test
    void delete(){
        teacherService.deleteFloor(1L);
    }

    @Test
    void TeacherTest() {
        teacherService.batchSaveStudent(new File("E:\\student.csv"));
        Region region = new Region(null, "美利坚特别行政区", "二等公民聚居地", new ArrayList<>());
        Building building = new Building(null, "北京天通苑", "家用小厕所", 1L, region,
            new ArrayList<>());
        Floor floor = new Floor(null, "L2", "阿巴阿巴", 1L, building, new ArrayList<>());
        Room room = new Room(null, "爱之窝", 1, "wdawdawd", 1, 1L, 1L, new ArrayList<>(), floor,
            null);
        teacherService.saveRegion(region);
        teacherService.saveBuilding(building);
        teacherService.saveFloor(floor);
        teacherService.saveRoom(room);
        room.setIntro("nimama");
        teacherService.updateRoom(room);
        Timeline timeline = new Timeline(null, 1, new Timestamp(1), new Timestamp(100000000000L),
            new Timestamp(10), new Timestamp(20), new Timestamp(20), new Timestamp(30),
            new Timestamp(30), new Timestamp(40));
        teacherService.saveTimeline(timeline);
    }

    @Test
    void SearchTest() {
        Region region = searchService.searchRegionById(1L);
        System.out.println(region);
        List<Building> building = searchService.searchBuilding(1L);
        System.out.println(building);
        List<Floor> floors = searchService.searchFloor(1L);
        System.out.println(floors);
        List<Room> rooms = searchService.searchRoom(1L);
        System.out.println(rooms);
    }


    @Test
    void StudentTest() {
        studentService.createGroup(1L, "冒险小虎队");
        Student student = searchService.searchStudentById(1L);
        student.setIntro("我爱洗澡皮肤好好");
        studentService.changeIntroduce(student);
        Comment comment = new Comment(null, "震惊", "西天取经", student.getStudentId()
            , 1L, new Timestamp(15153L), true);
        studentService.saveComment(comment);
        Student student1 = searchService.searchStudentById(2L);
        //studentService.joinGroup(student1, groupService.getGroupsList().get(0));
        studentService.memberLeave(student);
    }

    @Test
    void GroupTest() {
        groupService.getMemberList(searchService.searchGroupById(0L));

    }

    @Test
    void LoginTest() {

    }


    @Test
    void MsgTest() {
        Msg msg = new Msg(null, 1L, 2L, "罗启航牛逼", new Timestamp(12315616L), 12);
        msgService.saveMsg(msg);
    }


    @Test
    void RoomTest() {

    }

    @Test
    void TimelineTest() {

    }
}
