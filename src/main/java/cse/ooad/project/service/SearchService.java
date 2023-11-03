package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.FloorRepository;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Student> searchStudents(Long gender, Time awakeTime, Time sleepTime, Long type,
        String intro) {
        return studentRepository.getStudentsBySleepTimeLessThanAndAwakeTimeGreaterThanAndIntroLikeAAndGenderAAndType(
            sleepTime, awakeTime, intro, gender, type);

    }


    public Set<Group> searchGroups(Long gender, Time awakeTime, Time sleepTime,
        Long type, String intro) {
        List<Student> students = studentRepository.getStudentsBySleepTimeLessThanAndAwakeTimeGreaterThanAndIntroLikeAAndGenderAAndType(
            sleepTime, awakeTime, intro, gender, type);
        Set<Group> groupSet = new HashSet<>();
        students.forEach(t -> groupSet.add( t.getGroup()));
        return groupSet;
    }

    public List<Region> searchRegion() {
        return regionRepository.getRegions();
    }

    public List<Building> searchBuilding(Region region) {
        return buildingRepository.getBuildingsByRegionId(region.getRegionId());
    }

    public List<Floor> searchFloor(Building building) {
        return floorRepository.getFloorsByBuildingId(building.getBuildingId());
    }

    public List<Room> searchRoom(Floor floor) {
        return roomRepository.getRoomsByFloorId(floor.getFloorId());
    }


    public List<Student> searchStudentByName(String name) {
        return studentRepository.getStudentsByName(name);
    }


}
