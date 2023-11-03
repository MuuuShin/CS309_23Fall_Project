package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.repository.GroupRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import java.time.LocalTime;
import java.util.List;
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

    public List<Student> searchStudents(Long gender, LocalTime awakeTime, LocalTime sleepTime, Long type, String tags){
        return null;
    }

    public List<Group> searchGroups(Long gender, LocalTime awakeTime, LocalTime sleepTime, Long type, String tags){
        return null;
    }

    public List<Building> searchBuilding(Region region){
        return null;
    }

    public List<Floor> searchFloor(Region region, Building building){
        return null;
    }

    public List<Room> searchRoom(Region region, Building building, Floor floor){
        return null;
    }

    public Student searchStudent(String studentId){
        return null;
    }


}
