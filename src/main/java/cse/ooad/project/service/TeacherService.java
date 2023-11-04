package cse.ooad.project.service;


import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Teacher;
import cse.ooad.project.model.Timeline;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.FloorRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;
import cse.ooad.project.repository.TimelineRepository;
import java.io.File;
import java.sql.Timestamp;
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
    RoomRepository roomRepository;

    @Autowired
    FloorRepository floorRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    TimelineRepository timelineRepository;



    public void saveStudent(Student student){
        studentRepository.save(student);
    }

    public void deleteStudent(Student student){
        studentRepository.delete(student);
    }

    public void updateStudent(Student student){
        studentRepository.save(student);
    }

    public void saveFloor(Floor floor){
        floorRepository.save(floor);
    }

    public void deleteFloor(Floor floor){
        floorRepository.save(floor);
    }

    public void updateFloor(Floor floor){
        floorRepository.save(floor);
    }

    public void saveRegion(Region region){
        regionRepository.save(region);
    }

    public void deleteRegion(Region region){
        regionRepository.delete(region);
    }

    public void updateRegion(Region region){
        regionRepository.save(region);
    }

    public void saveRoom(Room room){
        roomRepository.save(room);
    }

    public void updateRoom(Room room){
        roomRepository.save(room);
    }

    public void deleteRoom(Room room){
        roomRepository.delete(room);
    }

    public void saveBuilding(Building building){
        buildingRepository.save(building);
    }

    public void updateBuilding(Building building){
        buildingRepository.save(building);
    }

    public void deleteBuilding(Building building){
        buildingRepository.delete(building);
    }

    public void saveTimeline(Timeline timeline){
        timelineRepository.save(timeline);
    }

    public void batchSaveStudent(File file){

    }


    /**
     * 获取宿舍选择情况
     */
    public File getAllStudentStatus(int type){
        return null;
    }
}
