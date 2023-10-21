package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Entity
@Table(name = "comments", schema = "public", catalog = "cs309a")
public class Comment {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "comment_id")
  private Long commentId;
  @Basic
  @Column(name = "title")
  private String title;
  @Basic
  @Column(name = "body")
  private String body;
  @Basic
  @Column(name = "account_id")
  private Long accountId;
  @Basic
  @Column(name = "post_id")
  private Long postId;
  @Basic
  @Column(name = "creation_date")
  private Timestamp creationTime;



  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public void setCreationTime(Timestamp creationTime) {
    this.creationTime = creationTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return commentId == comment.commentId && Objects.equals(title, comment.title) && Objects.equals(body, comment.body) && Objects.equals(accountId, comment.accountId) && Objects.equals(postId, comment.postId) && Objects.equals(creationTime, comment.creationTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(commentId, title, body, accountId, postId, creationTime);
  }
}
