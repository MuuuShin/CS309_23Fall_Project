package cse.ooad.project.repository;



import cse.ooad.project.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {


    Comment getCommentByCommentId(Long id);

    List<Comment> getCommentsByPostId(Long id);

    Integer deleteByCommentId(Long id);



}
