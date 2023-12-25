package cse.ooad.project;

import cse.ooad.project.model.*;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.service.*;
import cse.ooad.project.utils.StudentType;

import java.io.File;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
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
            //覆盖分隔符
            ScriptUtils.executeSqlScript(connection, new EncodedResource(new ClassPathResource("DDL.sql")), false, false, "--", ScriptUtils.EOF_STATEMENT_SEPARATOR, "/*", "*/");
        }
        System.out.println("setUp end");
    }

    @Order(2)
    @Test
    void TeacherTest() {
        teacherService.batchSaveStudent(new File("src/test/resources/student.csv"));
        //teacherService.batchSaveRoomNew(new File("src/test/resources/Rooms.csv"));
        teacherService.batchSaveRoom(new File("src/test/resources/university_data.csv"));

        Timeline timeline = new Timeline(null, 1, new Timestamp(10000L), new Timestamp(20000L),
                new Timestamp(20000), new Timestamp(30000), new Timestamp(30000), new Timestamp(40000),
                new Timestamp(40000), new Timestamp(50000));

        teacherService.saveTimeline(timeline);

    }

    @Order(3)
    @Test
    void StudentTest() {
        System.out.println(studentService.createGroup(200000001L, "冒险小虎队"));
        System.out.println(studentService.createGroup(200000002L, "多多探险队"));
        Student student = searchService.searchStudentByStudentId(200000001L);
        student.setIntro("我爱洗澡皮肤好好");
        student.setType(StudentType.MASTER_MALE.type);
        studentService.updateIntroduce(student);
        Comment comment = new Comment(null, "震惊", "西天取经", student.getStudentId()
                , 2L, new Timestamp(15153L), true);
        studentService.saveComment(comment);
        TimelineService.STATUS = 1;
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
        System.out.println(roomService.getGroupStarList(1L));
        System.out.println(roomService.getCommentsByRoom(1L));
    }


    @Order(5)
    @Test
    void GroupTest() {
        TimelineService.STATUS = 1;
        groupService.getMemberList(1L);
        System.out.println(groupService.starRoom(1L, 1L));
        System.out.println(groupService.starRoom(1L, 2L));
        System.out.println(groupService.starRoom(2L, 1L));
        System.out.println(groupService.starRoom(2L, 3L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        //System.out.println(groupService.getStarList(1L));
        TimelineService.STATUS = 2;
        System.out.println(groupService.chooseRoom(1L, 3L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        System.out.println(groupService.getGroupsList());
        System.out.println(groupService.chooseRoom(2L, 1L));
        TimelineService.STATUS = 3;
        Group group1 = searchService.searchStudentByStudentId(200000001L).getGroup();
        System.out.println(groupService.getMemberList(group1.getGroupId()));
        System.out.println(studentService.memberLeave(200000001L));
        System.out.println(groupService.getMemberList(group1.getGroupId()));
        System.out.println(studentService.createGroup(200000003L, "冒险小虎队1984"));

        System.out.println(groupService.chooseRoom(2L, 1L));
        System.out.println(groupService.chooseRoom(1L, 1L));
        System.out.println(groupService.chooseRoom(3L, 1L));
        //修改队长
        Student student = new Student(null, "白", "不爱洗澡皮肤好好", (short) 1, null, 1, null, null, "12110433", null);
        teacherService.saveStudent(student);
        studentService.createGroup(200000005L, "飞天茅台9999");
        System.out.println(groupService.changeLeader(2L, 200000004L));
        System.out.println(teacherService.transRoom(200000001L, 200000005L));

        groupService.test();

    }

    @Order(6)
    @Test
    void LoginTest() {
        System.out.println(loginService.loginStudent("12151515", "1546465465"));

    }

    @Order(7)
    @Test
    void MsgTest() {
        Msg msg = new Msg(null, 0, 1L, 2L, "罗启航牛逼", new Timestamp(12315616L), 12);
        msgService.saveAndForwardMsg(msg);
    }

    @Order(8)
    @Test
    void TimelineTest() {
        TimelineService.timestamp = 1000;
        System.out.println(timelineService.getStage(1));
        TimelineService.timestamp = 10100;
        System.out.println(timelineService.getStage(1));
        TimelineService.timestamp = 20200;
        System.out.println(timelineService.getStage(1));
        TimelineService.timestamp = 30300;
        System.out.println(timelineService.getStage(1));
        TimelineService.timestamp = 40400;
        System.out.println(timelineService.getStage(1));
    }

    @Order(9)
    @Test
    void SearchTest() {
        Region region = searchService.searchRegionByRegionId(1L);
        System.out.println(region);
        List<Building> building = searchService.searchBuildingByRegion(1L);
        System.out.println(building);
        List<Floor> floors = searchService.searchFloorByFloor(1L);
        System.out.println(floors);
        List<Room> rooms = searchService.searchRoomByFloor(1L);
        System.out.println(rooms);
        Student student = searchService.searchStudentByStudentId(200000001L);
        student.setAwakeTime(new Time(10, 10, 10));
        student.setSleepTime(new Time(20, 0, 0));
        student.setIntro("我爱洗澡皮肤好好");
        studentService.updateIntroduce(student);
        System.out.println("查学生");
        System.out.println(searchService.searchStudents(1L, new Time(9, 12, 1), new Time(2, 1, 5), 1L, "洗澡"));
        System.out.println(searchService.searchStudents(1L, new Time(10, 9, 1), new Time(2, 1, 5), 1L, "洗澡"));
        System.out.println(searchService.searchStudents(1L, new Time(14, 9, 1), new Time(11, 1, 5), 1L, "洗澡"));
        System.out.println(searchService.searchCommentByCommentId(1L));
        System.out.println(searchService.searchGroups(1L, new Time(9, 12, 1), new Time(22, 1, 5), 1L, "洗澡"));
        System.out.println(searchService.searchMsgByMsgId(1L));
    }


    @Order(10)
    @Test
    void deleteTest() {
        //studentService.deleteComment(1L, 200000001L);
        //teacherService.deleteStudent(200000003L);
        System.out.println("delete");
       //teacherService.deleteRoom(1L);
        System.out.println("delete");
        //teacherService.deleteFloor(1L);
        System.out.println("delete");
        //teacherService.deleteBuilding(1L);
        //teacherService.deleteRegion(1L);
        //teacherService.deleteRoom(1L);
    }

    @Order(11)
    @Test
    void wsTest(){
        Floor floor = new Floor();
        System.out.println(floor.getFloorId());
    }

    @Order(12)
    @Test
    void complexTest(){
        System.out.println("test");
        TimelineService.STATUS = 3;
        TimelineService.timestamp = 0;
        System.out.println(groupService.chooseRoom(1L, 2127L));
        teacherService.batchOutputStudent();
    }

}

