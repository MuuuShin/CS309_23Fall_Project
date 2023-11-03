package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.FloorRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    RegionRepository regionRepository;



    public void saveStudent(Student student){}

    public void deleteStudent(Student student){}

    public void updateStudent(Student student){}

    public void saveFloor(Floor floor){}

    public void deleteFloor(Floor floor){}

    public void updateFloor(Floor floor){}

    public void saveRegion(Region region){}

    public void deleteRegion(Region region){}

    public void updateRegion(Region region){}

    public void saveRoom(Teacher teacher, Room room){}

    public void updateRoom(Room room){}

    public void deleteRoom(Room room){}

    public void saveBuilding(Building building){}

    public void updateBuilding(Building building){}

    public void deleteBuilding(Building building){}

    public boolean saveTimeline(int type, int period, LocalDateTime begin, LocalDateTime end){
        return false;
    }

    public void batchsaveStudent(File file){}


    /**
     * 获取宿舍选择情况
     */
    public File getAllStudentStatus(int type){
        return null;
    }
}
