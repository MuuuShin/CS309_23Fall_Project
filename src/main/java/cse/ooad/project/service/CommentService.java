package cse.ooad.project.service;


import cse.ooad.project.model.Comment;
import cse.ooad.project.model.Room;
import cse.ooad.project.repository.CommentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public void addComment(Comment comment){
    }

    public void deleteComment(Comment comment){

    }

    public List<Comment> getCommentsByRoom(Room room){
        return null;
    }



}
