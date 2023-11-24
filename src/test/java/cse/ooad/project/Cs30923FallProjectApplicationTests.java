package cse.ooad.project;

import cse.ooad.project.model.*;
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
import cse.ooad.project.utils.StudentType;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@EnableCaching
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
//
//    @Autowired
//    private CommentRepository commentRepository;

    @Autowired
    private DataSource dataSource;

    @Order(1)
    @Test
    public void setUp() throws SQLException {
        System.out.println("setUp");
        //执行
        // 获取数据库连接
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("drop.sql"));
            // 使用 Spring 的 ScriptUtils 执行 SQL 文件
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("DDL.sql"));
        }
        System.out.println("setUp end");
    }

    @Order(2)
    @Test
    void TeacherTest() {
        teacherService.batchSaveStudent(new File("src/test/resources/student.csv"));
        teacherService.batchSaveRoom(new File("src/test/resources/Rooms.csv"));

        Timeline timeline = new Timeline(null, 1, new Timestamp(1), new Timestamp(100000000000L),
                new Timestamp(10), new Timestamp(20), new Timestamp(20), new Timestamp(30),
                new Timestamp(30), new Timestamp(40));
        teacherService.saveTimeline(timeline);
    }


    @Order(3)
    @Test
    void StudentTest() {
        System.out.println(studentService.createGroup(200000001L, "冒险小虎队"));
        System.out.println(studentService.createGroup(200000002L, "多多探险队"));
        Student student = searchService.searchStudentById(200000001L);
        student.setIntro("我爱洗澡皮肤好好");
        student.setType(StudentType.MASTER_MALE.type);
        studentService.changeIntroduce(student);
        Comment comment = new Comment(null, "震惊", "西天取经", student.getStudentId()
                , 2L, new Timestamp(15153L), true);
        studentService.saveComment(comment);
        System.out.println(studentService.joinGroup(200000003L, 2L));
        System.out.println(studentService.joinGroup(200000003L, 1L));
        System.out.println(studentService.joinGroup(200000004L, 2L));
        System.out.println(studentService.joinGroup(200000004L, 1L));

    }

    @Order(4)
    @Test
    void RoomTest() {
        System.out.println(roomService.getGroupStarList(2L));
        System.out.println(roomService.getCommentsByRoom(2L));
    }


    @Order(5)
    @Test
    void GroupTest() {
        TimelineService.STATUS = 1;
        groupService.getMemberList(1L);
        System.out.println(groupService.starRoom(1L, 1L));
        System.out.println(groupService.starRoom(1L, 2L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        //System.out.println(groupService.getStarList(1L));
        TimelineService.STATUS = 2;
        System.out.println(groupService.chooseRoom(1L, 3L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        System.out.println(groupService.getGroupsList());
        System.out.println(groupService.chooseRoom(2L, 1L));
        TimelineService.STATUS = 3;
        studentService.memberLeave(200000003L);
        studentService.createGroup(200000003L, "冒险小虎队1984");
        System.out.println(groupService.chooseRoom(2L, 1L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        System.out.println(groupService.chooseRoom(3L, 1L));

        groupService.test();

    }

    @Order(6)
    @Test
    void LoginTest() {

    }

    @Order(7)
    @Test
    void MsgTest() {
        Msg msg = new Msg(null, 1L, 2L, "罗启航牛逼", new Timestamp(12315616L), 12);
        msgService.saveMsg(msg);
    }


    @Order(8)
    @Test
    void TimelineTest() {

    }

    @Order(9)
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

        System.out.println(searchService.searchCommentById(1L));
        System.out.println(searchService.searchMsgById(1L));
        System.out.println(searchService.searchStudents());
    }

}
