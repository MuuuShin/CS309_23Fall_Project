package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {


    @Autowired
    RoomRepository roomRepository;

    @Autowired
    CommentRepository commentRepository;


    @Transactional
    public List<Group> getGroupStarList(Long id) {
        return roomRepository.getRoomsByRoomId(id).getGroupStarList();
    }


    public List<Comment> getCommentsByRoom(Long id) {
        Room room = roomRepository.getRoomsByRoomId(id);
        int index = 0;
        List<Comment> commentList = new ArrayList<>();
        commentList.add(commentRepository.getCommentByCommentId(room.getCommentBaseId()));
        while (index < commentList.size()) {
            //弄一个队列，如果可视就入队
            List<Comment> list =  commentRepository.getCommentsByPostId(commentList.get(index).getPostId());
            list.forEach(t -> {if (t.getDisabled()) {
                commentList.add(t);
            }});

            index++;
        }
        return commentList;
    }


}
