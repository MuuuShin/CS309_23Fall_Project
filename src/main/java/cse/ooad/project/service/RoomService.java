package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
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



    public List<Group> getGroupStarList(Room room){
        return room.getGroupStarList();
    }



    public List<Comment> getCommentsByRoom(Room room){
        return null;
    }



}
