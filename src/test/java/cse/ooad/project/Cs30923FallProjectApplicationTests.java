package cse.ooad.project;

import cse.ooad.project.model.*;

import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.service.MsgService;

import cse.ooad.project.service.RoomService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.service.TimelineService;
import java.io.File;

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






	@Test
	void TeacherTest(){
		teacherService.batchSaveStudent( new File("E:\\student.csv"));
		Region region = new Region(null, "美利坚特别行政区","二等公民聚居地", new ArrayList<>());
		Building building = new Building(null, "北京天通苑","家用小厕所", 1L, region, new ArrayList<>());
		Floor floor = new Floor(null, "L2", "阿巴阿巴", 1L, building, new ArrayList<>());
		Room room = new Room(null, "爱之窝", 1, "wdawdawd", 1, 1L,1L,new ArrayList<>(), floor, null);
		teacherService.saveRegion(region);
		teacherService.saveBuilding(building);
		teacherService.saveFloor(floor);
		teacherService.saveRoom(room);
		room.setIntro("nimama");
		teacherService.updateRoom(room);
	}

	@Test
	void SearchTest(){
	Region region = searchService.searchRegionById(1L);
		System.out.println(region);
	List<Building> building = searchService.searchBuilding(region);
		System.out.println(building);
	List<Floor> floors = searchService.searchFloor(building.get(0));
		System.out.println(floors);
	List<Room> rooms = searchService.searchRoom(floors.get(0));
		System.out.println(rooms);
	}


	@Test
	void StudentTest(){

	}

	@Test
	void GroupTest(){

	}

	@Test
	void LoginTest(){

	}


	@Test
	void MsgTest(){

	}


	@Test
	void RoomTest(){}

	@Test

	void TimelineTest(){

	}
}
