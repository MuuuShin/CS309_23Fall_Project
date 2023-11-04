package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Group;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.CommentRepository;
import cse.ooad.project.repository.RoomRepository;
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


    public List<Group> getGroupStarList(Room room) {
        return room.getGroupStarList();
    }


    public List<Comment> getCommentsByRoom(Room room) {
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
