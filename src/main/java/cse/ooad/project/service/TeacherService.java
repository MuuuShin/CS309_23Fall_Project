package cse.ooad.project.service;


import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import cse.ooad.project.model.*;
import cse.ooad.project.repository.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import java.util.stream.Collectors;
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
    @Autowired
    private GroupRepository groupRepository;


    public Student saveStudent(Student student) {
        //todo: password
        return studentRepository.save(student);
    }


    @Transactional
    public Boolean deleteStudent(Long id) {

        return studentRepository.deleteByStudentId(id) != 0;
    }

    public Student updateStudent(Student student) {
        Student old = studentRepository.getStudentByStudentId(student.getStudentId());
        old.setSleepTime(student.getSleepTime());
        old.setAwakeTime(student.getAwakeTime());
        old.setIntro(student.getIntro());
        return studentRepository.save(old);
    }

    public Floor saveFloor(Floor floor) {
        return floorRepository.save(floor);
    }


    @Transactional
    public Boolean deleteFloor(Long id) {
        if (floorRepository.findById(id).isPresent()) {
            List<Long> ids = new ArrayList<>();
            ids.add(id);
            List<Room> rooms = roomRepository.findAllWithGroupStarListByBuildingIds(ids);
            System.out.println(rooms);
            List<Group> groupList = new ArrayList<>();
            rooms.forEach(t->{
                List<Group> groups = t.getGroupStarList();
                groups.forEach(e -> e.getRoomStarList().remove(t));
                groupList.addAll(groups);
            });
            groupRepository.saveAll(groupList);
            roomRepository.deleteAll(rooms);
        }
        System.out.println("结束*******************");

        return floorRepository.removeByFloorId(id) != 0;
    }

    public Floor updateFloor(Floor floor) {
        return floorRepository.save(floor);
    }

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }


    @Transactional
    public Boolean deleteRegion(Long id) {
        if (regionRepository.findById(id).isPresent()) {
            List<Building> lists = regionRepository.findById(id).get().getBuildingList();
            List<Long> ids = lists.stream().map(Building::getBuildingId).toList();
            List<Floor> floors = floorRepository.findAllByBuildingIdIn(ids);
            ids = floors.stream().map(Floor::getFloorId).toList();
            List<Room> rooms = roomRepository.findAllWithGroupStarListByBuildingIds(ids);
            List<Group> groupList = new ArrayList<>();
            rooms.forEach(t->{
                List<Group> groups = t.getGroupStarList();
                groups.forEach(e -> e.getRoomStarList().remove(t));
                groupList.addAll(groups);
            });
            groupRepository.saveAll(groupList);
            roomRepository.deleteAll(rooms);
            floorRepository.deleteAll(floors);
            buildingRepository.deleteAll(lists);
            regionRepository.deleteById(id);
        }
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

    @Transactional
    public Boolean deleteRoomStarList(Long roomId) {
        Room room = roomRepository.getRoomsByRoomId(roomId);
        if (room == null) {
            return false;
        }
        List<Group> groupList = room.getGroupStarList();

        for (Group group : groupList) {
            group.getRoomStarList().remove(room);

        }groupRepository.saveAll(groupList);
        return true;
    }

    @Transactional
    public Boolean deleteRoom(Long id) {
        if (deleteRoomStarList(id)) {
            return roomRepository.deleteByRoomId(id) != 0;
        }
        return false;
    }

    public Building saveBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Building updateBuilding(Building building) {
        return buildingRepository.save(building);
    }


    @Transactional
    public Boolean deleteBuilding(Long id) {
        if (buildingRepository.findById(id).isPresent()) {
            List<Floor> floors = floorRepository.getFloorsByBuildingId(id);
            List<Long> ids = floors.stream().map(Floor::getFloorId).toList();
            List<Room> rooms = roomRepository.findAllWithGroupStarListByBuildingIds(ids);
            List<Group> groupList = new ArrayList<>();
            rooms.forEach(t->{
                List<Group> groups = t.getGroupStarList();
                groups.forEach(e -> e.getRoomStarList().remove(t));
                groupList.addAll(groups);
            });
            groupRepository.saveAll(groupList);
            roomRepository.deleteAll(rooms);
            floorRepository.deleteAll(floors);
        }
        return buildingRepository.removeByBuildingId(id) != 0;
    }

    public Timeline saveTimeline(Timeline timeline) {
        return timelineRepository.save(timeline);
    }


    //以学生为单位调换宿舍
    public Boolean transRoom(Long id1, Long id2) {
        Student student1 = studentRepository.getStudentByStudentId(id1);
        Student student2 = studentRepository.getStudentByStudentId(id2);
        if (!Objects.equals(student1.getType(), student2.getType())) {
            return false;
        }

        Group group1 = student1.getGroup();
        Group group2 = student2.getGroup();

        if (group1 != null) {
            student2.setGroupId(group1.getGroupId());
            if (Objects.equals(group1.getLeader(), student1.getStudentId())) {
                group1.setLeader(student2.getStudentId());
            }
        }
        if (group2 != null) {
            student1.setGroupId(group2.getGroupId());
            if (Objects.equals(group2.getLeader(), student2.getStudentId())) {
                group2.setLeader(student1.getStudentId());
            }
        }
        student1.setGroupId(group2.getGroupId());
        student2.setGroupId(group1.getGroupId());
        studentRepository.save(student1);
        studentRepository.save(student2);
        groupRepository.save(group1);
        groupRepository.save(group2);
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


    public void batchSaveRoomNew(File file) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, StandardCharsets.UTF_8),
                    CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 1);
            String[] strs;

            List<String> nameList = new ArrayList<>();
            List<Integer> typeList = new ArrayList<>();
            List<String> introList = new ArrayList<>();
            List<String> floorNameList = new ArrayList<>();
            List<String> buildingNameList = new ArrayList<>();
            List<String> regionNameList = new ArrayList<>();

            while ((strs = csvReader.readNext()) != null) {
                nameList.add(strs[6]);
                introList.add(strs[7]);
                typeList.add(Integer.parseInt(strs[8]));
                floorNameList.add(strs[4]);
                buildingNameList.add(strs[2]);
                regionNameList.add(strs[0]);
            }
            csvReader.close();
            roomRepository.batchInsertRoomDataFunction(
                    nameList.toArray(new String[0]),
                    typeList.toArray(new Integer[0]),
                    introList.toArray(new String[0]),
                    floorNameList.toArray(new String[0]),
                    buildingNameList.toArray(new String[0]),
                    regionNameList.toArray(new String[0])
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
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
                if (regionHashMap.get(region.getName()) == null) {
                    region = regionRepository.save(region);
                    regionHashMap.put(region.getName(), region);
                } else {
                    region = regionHashMap.get(region.getName());
                }


                Building building = new Building();
                building.setName(strs[2]);
                building.setIntro(strs[3]);
                building.setRegionId(region.getRegionId());
                if (buildingHashMap.get(region.getName() + building.getName()) == null) {
                    building = buildingRepository.save(building);
                    buildingHashMap.put(region.getName() + building.getName(), building);
                } else {
                    building = buildingHashMap.get(region.getName() + building.getName());
                }


                Floor floor = new Floor();
                floor.setName(strs[4]);
                floor.setIntro(strs[5]);
                floor.setBuildingId(building.getBuildingId());

                if (floorHashMap.get(region.getName() + building.getName() + floor.getName()) == null) {
                    floor = floorRepository.save(floor);
                    floorHashMap.put(region.getName() + building.getName() + floor.getName(), floor);
                } else {
                    floor = floorHashMap.get(region.getName() + building.getName() + floor.getName());
                }

                Room room = new Room();
                room.setName(strs[6]);
                room.setIntro(strs[7]);
                room.setType(Integer.parseInt(strs[8]));
                room.setStatus(Integer.parseInt(strs[9]));
                room.setFloorId(floor.getFloorId());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Comment comment = new Comment(null, strs[6], null, room.getRoomId(), 0L, timestamp, false);
                comment = commentRepository.save(comment);
                room.setCommentBaseId(comment.getCommentId());
                rooms.add(room);
            }
            roomRepository.saveAll(rooms);
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void batchOutputStudent(){
        List<Group> group = groupRepository.findAll();
        HashMap<Student, String> map = new HashMap<>();
        group.forEach(t->{
            //把所有加入队伍了切队伍选定了房间的学生都放进去
            if (t.getRoomId() == null){
                return;
            }else{
                Room room = roomRepository.getRoomsByRoomId(t.getRoomId());
                Floor floor = floorRepository.getFloorByFloorId(room.getFloorId());
                Building building = buildingRepository.getBuildingByBuildingId(floor.getBuildingId());
                Region region = regionRepository.getRegionByRegionId(building.getRegionId());
                List<Student> students = t.getMemberList();
                students.forEach(e->{
                    map.put(e, region.getName()+","+building.getName()+","+floor.getName()+","+room.getName());
                });
            }
        });
        List<Student> students = studentRepository.findAll();
        students.forEach(t->{
            map.putIfAbsent(t, "未分配");
        });
        System.out.println(map);
        students.sort((Comparator.comparing(Student::getAccount)));
        try {
            File file = new File("src/main/resources/output.csv");
            if (!file.exists()) {
                 file.createNewFile();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("学号,姓名,宿舍");
            bufferedWriter.newLine();
            students.forEach(t -> {
                try {
                    bufferedWriter.write(t.getAccount() + "," + t.getName() + "," + map.get(t));
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
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
