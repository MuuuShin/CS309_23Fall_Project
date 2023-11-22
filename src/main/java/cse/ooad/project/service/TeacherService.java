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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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


    public Student saveStudent(Student student) {
        //todo: password
        return studentRepository.save(student);
    }

    public Boolean deleteStudent(Long id) {
        return studentRepository.deleteByStudentId(id) != 0;
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Floor saveFloor(Floor floor) {
        return floorRepository.save(floor);
    }


    @Transactional
    public Boolean deleteFloor(Long id) {
        return floorRepository.removeByFloorId(id) != 0;
    }

    public Floor updateFloor(Floor floor) {
         return floorRepository.save(floor);
    }

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }

    public Boolean deleteRegion(Long id) {
        return regionRepository.deleteByRegionId(id) != 0;
    }

    public Region updateRegion(Region region) {
       return regionRepository.save(region);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Room room) {
        return roomRepository.save(room);
    }

    public Boolean deleteRoom(Long id) {
         return roomRepository.deleteByRoomId(id) != 0;
    }

    public Building saveBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Building updateBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Boolean deleteBuilding(Long id) {
        return buildingRepository.removeByBuildingId(id) != 0;
    }

    public Timeline saveTimeline(Timeline timeline) {
        return timelineRepository.save(timeline);
    }

    public Boolean transRoom(Long id1, Long id2){
        Student student1 = studentRepository.getStudentByStudentId(id1);
        Student student2 = studentRepository.getStudentByStudentId(id2);
        if (!Objects.equals(student1.getType(), student2.getType())){
            return false;
        }

        Group group1 = student1.getGroup();
        Group group2 = student2.getGroup();
        if (group1 != null){
            student2.setGroupId(group1.getGroupId());
            if (Objects.equals(group1.getLeader(), student1.getStudentId())){
                group1.setLeader(student2.getStudentId());
            }
        }
        if (group2 != null){
            student1.setGroupId(group2.getGroupId());
            if (Objects.equals(group2.getLeader(), student2.getStudentId())){
                group2.setLeader(student1.getStudentId());
            }
        }
        return true;
    }

    public void batchSaveStudent(File file) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8),
                CSVParser.DEFAULT_SEPARATOR,
                CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;
            List<Student> list = new ArrayList<>();
            List<Password> passwordList = new ArrayList<>();
            while ((strs = csvReader.readNext()) != null) {
                Student student = new Student();
                Password password = new Password();

                student.setName(strs[1]);
                student.setGender(Short.parseShort(strs[2]));
                student.setAccount(strs[3]);
                student.setType(Integer.parseInt(strs[5]));
                password.setPassword(strs[4]);
                password.setAccount(strs[3]);
                passwordList.add(password);
                list.add(student);
            }
            studentRepository.saveAll(list);
            passwordRepository.saveAll(passwordList);
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

            HashMap<String, Region> regionHashMap = new HashMap<>();
            HashMap<String, Building> buildingHashMap = new HashMap<>();
            HashMap<String, Floor> floorHashMap = new HashMap<>();

            List<Room> rooms = new ArrayList<>();
            while ((strs = csvReader.readNext()) != null) {
                Region region = new Region();
                region.setName(strs[0]);
                region.setIntro(strs[1]);
                if(regionHashMap.get(region.getName()) == null){
                    region = regionRepository.save(region);
                    regionHashMap.put(region.getName(), region);
                }else {
                    region = regionHashMap.get(region.getName());
                }


                Building building = new Building();
                building.setName(strs[2]);
                building.setIntro(strs[3]);
                building.setRegionId(region.getRegionId());
                if (buildingHashMap.get(region.getName() + building.getName()) == null){
                    building = buildingRepository.save(building);
                    buildingHashMap.put(region.getName() + building.getName(), building);
                }else {
                    building = buildingHashMap.get(region.getName() + building.getName());
                }


                Floor floor = new Floor();
                floor.setName(strs[4]);
                floor.setIntro(strs[5]);
                floor.setBuildingId(building.getBuildingId());

                if (floorHashMap.get(region.getName()+building.getName()+floor.getName()) == null){
                    floor = floorRepository.save(floor);
                    floorHashMap.put(region.getName()+building.getName()+floor.getName(), floor);
                }else {
                    floor = floorHashMap.get(region.getName()+building.getName()+floor.getName());
                }

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
                rooms.add(room);

            }
            roomRepository.saveAll(rooms);
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
