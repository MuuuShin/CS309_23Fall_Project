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
import org.springframework.transaction.annotation.Transactional;

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


    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    public void saveFloor(Floor floor) {
        floorRepository.save(floor);
    }


    @Transactional
    public Boolean deleteFloor(Long id) {
        return floorRepository.removeByFloorId(id) != 0;
    }

    public void updateFloor(Floor floor) {
        floorRepository.save(floor);
    }

    public void saveRegion(Region region) {
        regionRepository.save(region);
    }

    public void deleteRegion(Region region) {
        regionRepository.delete(region);
    }

    public void updateRegion(Region region) {
        regionRepository.save(region);
    }

    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void deleteBuilding(Building building) {
        buildingRepository.delete(building);
    }

    public void saveTimeline(Timeline timeline) {
        timelineRepository.save(timeline);
    }

    public void batchSaveStudent(File file) {
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
        }


    }


    /**
     * 获取宿舍选择情况
     */
    public File getAllStudentStatus(int type) {
        return null;
    }
}
