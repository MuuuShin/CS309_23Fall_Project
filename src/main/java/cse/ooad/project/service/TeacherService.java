package cse.ooad.project.service;


import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import cse.ooad.project.model.Building;
import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Region;
import cse.ooad.project.model.Room;
import cse.ooad.project.model.Student;
import cse.ooad.project.model.Timeline;
import cse.ooad.project.repository.BuildingRepository;
import cse.ooad.project.repository.FloorRepository;
import cse.ooad.project.repository.RegionRepository;
import cse.ooad.project.repository.RoomRepository;
import cse.ooad.project.repository.StudentRepository;
import cse.ooad.project.repository.TeacherRepository;
import cse.ooad.project.repository.TimelineRepository;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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


    public Object saveStudent(Student student) {
        studentRepository.save(student);
        return null;
    }

    public Object deleteStudent(Student student) {
        studentRepository.delete(student);
        return null;
    }

    public Object updateStudent(Student student) {
        studentRepository.save(student);
        return null;
    }

    public Object saveFloor(Floor floor) {
        floorRepository.save(floor);
        return null;
    }

    public Object deleteFloor(Floor floor) {
        floorRepository.save(floor);
        return null;
    }

    public Object updateFloor(Floor floor) {
        floorRepository.save(floor);
        return null;
    }

    public Object saveRegion(Region region) {
        regionRepository.save(region);
        return null;
    }

    public Object deleteRegion(Region region) {
        regionRepository.delete(region);
        return null;
    }

    public Object updateRegion(Region region) {
        regionRepository.save(region);
        return null;
    }

    public Object saveRoom(Room room) {
        roomRepository.save(room);
        return null;
    }

    public Object updateRoom(Room room) {
        roomRepository.save(room);
        return null;
    }

    public Object deleteRoom(Room room) {
        roomRepository.delete(room);
        return null;
    }

    public Object saveBuilding(Building building) {
        buildingRepository.save(building);
        return null;
    }

    public Object updateBuilding(Building building) {
        buildingRepository.save(building);
        return null;
    }

    public Object deleteBuilding(Building building) {
        buildingRepository.delete(building);
        return null;
    }

    public Object saveTimeline(Timeline timeline) {
        timelineRepository.save(timeline);
        return null;
    }

    public boolean batchSaveStudent(File file) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8),
                CSVParser.DEFAULT_SEPARATOR,
                CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;
            List<Student> list = new ArrayList<>();
            while ((strs = csvReader.readNext()) != null) {
                Student student = new Student();
                student.setName(strs[1]);
                student.setAccount(strs[2]);
                student.setPassword(strs[3]);
                student.setType(Integer.parseInt(strs[4]));
                list.add(student);
            }
            studentRepository.saveAll(list);



            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }


    /**
     * 获取宿舍选择情况
     */
    public File getAllStudentStatus(int type) {
        return null;
    }
}
