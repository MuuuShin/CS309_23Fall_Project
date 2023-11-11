package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * {@link  Comment}用于表示评论信息的实体类，包括评论的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>commentId: 评论ID，唯一标识评论。</li>
 *   <li>title: 评论标题。</li>
 *
 *   <li>body: 评论内容。</li>
 *   <li>userId: 评论发表者的帐户ID。老师和学生都可以评论。</li>
 *   <li>postId: 评论所属的帖子ID。</li>
 *   <li>creationTime: 评论创建时间。</li>
 *   <li>disabled: 评论是否被禁用。</li>
 *   <li>[映射][未使用]post: 评论所属的帖子。</li>
 *   <li>[映射][未使用]replyList: 评论的回复列表。</li>
 * </ul>
 * 评论的嵌套方式：<br>
 * 每个房间拥有一条元评论，元评论的postId为0，userId为房间的ID。<br>
 * 对这个房间发起的评论视为对元评论的回复，其postId为元评论的commentId，userId为发起评论的用户ID。<br>
 * 若对评论发起的评论，其postId为被回复的评论的commentId，userId为发起评论的用户ID。<br>
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

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
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "post_id")
    private Long postId;
    @Basic
    @Column(name = "creation_date")
    private Timestamp creationTime;
    @Basic
    @Column(name = "disabled")
    private Boolean disabled;

    /* 映射实体 */
//    todo: 不加了 增加工作量 或者加上也可以?再说
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", insertable = false, updatable = false)
//    private Comment post;
//
//    @Exclude
//    @JsonIgnore
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Comment> replyList;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentId, comment.commentId) && Objects.equals(title, comment.title) && Objects.equals(body, comment.body) && Objects.equals(userId, comment.userId) && Objects.equals(postId, comment.postId) && Objects.equals(creationTime, comment.creationTime) && Objects.equals(disabled, comment.disabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, title, body, userId, postId, creationTime, disabled);
    }
}
