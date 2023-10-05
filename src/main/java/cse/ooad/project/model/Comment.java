package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "comment")
@Data
public class Comment {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "edited")
  private boolean edited;

  @Column(name = "post_id")
  private Long postId;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "post_id", insertable = false, updatable = false)
  private Answer answer;
  @Column(name = "body")
  private String body;

  @Column(name = "creation_date")
  private Timestamp creationDate;

  @Column(name = "score")
  private int score;

  @Column(name = "content_license")
  private String contentLicense;

  @Column(name = "account_id")
  private Long accountId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return commentId == comment.commentId && edited == comment.edited && postId == comment.postId && score == comment.score && accountId == comment.accountId && Objects.equals(body, comment.body) && Objects.equals(creationDate, comment.creationDate) && Objects.equals(contentLicense, comment.contentLicense);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commentId, edited, postId, body, creationDate, score, contentLicense, accountId);
  }
}
