package cse.ooad.project;

import cse.ooad.project.model.Building;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.service.MsgService;
import cse.ooad.project.service.NotificationService;
import cse.ooad.project.service.RoomService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.service.TimelineService;
import java.util.HashMap;
import java.util.HashSet;
import org.checkerframework.checker.units.qual.A;
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


	@Test
	void StudentTest(){
		Student student = new Student( 	);
		student.setIntro("我爱体测");
		student.setPassword("12110425");
		student.setAccount("12110425");

	}
}
