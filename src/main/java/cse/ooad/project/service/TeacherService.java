package cse.ooad.project.service;


import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import cse.ooad.project.model.*;
import cse.ooad.project.repository.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    private CommentRepository commentRepository;


    public void saveStudent(Student student) {
        //todo: password
        studentRepository.save(student);
    }

    public Boolean deleteStudent(Long id) {
        return studentRepository.deleteByStudentId(id) != 0;
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

    public Boolean deleteRegion(Long id) {
        return regionRepository.deleteByRegionId(id) != 0;
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

    public Boolean deleteRoom(Long id) {
         return roomRepository.deleteByRoomId(id) != 0;
    }

    public void saveBuilding(Building building) {
        buildingRepository.save(building);
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }

    public Boolean deleteBuilding(Long id) {
        return buildingRepository.removeByBuildingId(id) != 0;
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
                Password password = new Password(strs[2], strs[3]);
                passwordRepository.save(password);
                student.setType(Integer.parseInt(strs[4]));
                list.add(student);
            }
            studentRepository.saveAll(list);
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batchSaveRoom(File file) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8),
                CSVParser.DEFAULT_SEPARATOR,
                CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;
            while ((strs = csvReader.readNext()) != null) {
                Region region = new Region();
                region.setName(strs[0]);
                region.setIntro(strs[1]);
                region = regionRepository.save(region);
                Building building = new Building();
                building.setName(strs[2]);
                building.setIntro(strs[3]);
                building.setRegionId(region.getRegionId());
                building = buildingRepository.save(building);
                Floor floor = new Floor();
                floor.setName(strs[4]);
                floor.setIntro(strs[5]);
                floor.setBuildingId(building.getBuildingId());
                floor = floorRepository.save(floor);
                Room room = new Room();
                room.setName(strs[6]);
                room.setIntro(strs[7]);
                room.setType(Integer.parseInt(strs[8]));
                room.setStatus(Integer.parseInt(strs[9]));
                room.setFloorId(floor.getFloorId());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Comment comment=new Comment(null,strs[6],null,room.getRoomId(),0L,timestamp,false);
                commentRepository.save(comment);
                room.setCommentBaseId(comment.getCommentId());
                roomRepository.save(room);
                System.out.println(room);
            }
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
