package cse.ooad.project;


import cse.ooad.project.controller.Result;
import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.service.GroupService;
import cse.ooad.project.service.LoginService;
import cse.ooad.project.service.MsgService;
import cse.ooad.project.service.RoomService;
import cse.ooad.project.service.SearchService;
import cse.ooad.project.service.StudentService;
import cse.ooad.project.service.TeacherService;
import cse.ooad.project.service.TimelineService;
import cse.ooad.project.utils.RoomType;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.Transactional;

@EnableCaching
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test2 {

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
    @Transactional
    void contextLoads() {
        Long regionId = 1L;
        Long studentType = 1L;//你自己加上type

        List<Building> buildings = searchService.searchBuildingByRegion(regionId);
//        List<Floor> floors = searchService.searchFloorByFloor(1L);
        buildings.forEach(t -> {
            t.getFloorList().forEach(f -> {
                f.setRoomList(f.getRoomList().stream()
                    .filter(r -> RoomType.valueOf(r.getType()).getStudentType() == studentType)
                    .toList());
            });
        });
//                floors.forEach(f -> f.setRoomList(f.getRoomList().stream()
//                    .filter(r -> RoomType.valueOf(r.getType()).getStudentType() == studentType)
//                    .toList()));

    }


}
