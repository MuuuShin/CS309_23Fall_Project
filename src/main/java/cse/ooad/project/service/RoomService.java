package cse.ooad.project.service;


import cse.ooad.project.model.Floor;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.RoomRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {


    @Autowired
    RoomRepository roomRepository;


    /**
     * 添加一个新的room
     */
    void saveRoom(Room room){

    }

    void updateRoom(Room room){}

    void deleteRoom(Room room){}

    public List<Room> getRoomListByFloors(Floor floor){
        return null;
    }

    public List<Group> getGroupStarList(Room room){
        return room.getGroupStarList();
    }


    /**
     *根据status和floor获取房间，便于搜索，floor可以为null，null则搜索全部
     * @param status
     * @return
     */
    public List<Room> getRoomsByStatusAndFloor(Long status, Floor floor){
        return null;
    }

    public List<Room> getRoomsByName(String name){
        return null;
    }

    public void updateStatus(Room room, Long status){}





}
