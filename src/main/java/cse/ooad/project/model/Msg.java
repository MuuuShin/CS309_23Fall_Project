package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {@link Msg} 用于表示消息的实体类，包括消息的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>msgId: 消息的唯一标识。</li>
 *   <li>srcId: 消息发送者的标识。</li>
 *   <li>dstId: 消息接收者的标识。</li>
 *   <li>body: 消息内容。</li>
 *   <li>timestamp: 消息时间戳，表示消息发送的时间。</li>
 *   <li>status: 消息是否已读的状态。UNREAD,READ 0,1</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "msgs", schema = "public", catalog = "cs309a")
public class Msg {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "msg_id")
    private long msgId;
    @Basic
    @Column(name = "src_id")
    private Long srcId;
    @Basic
    @Column(name = "dst_id")
    private Long dstId;
    @Basic
    @Column(name = "body")
    private String body;
    @Basic
    @Column(name = "timestamp")
    private Timestamp timestamp;
    @Basic
    @Column(name = "status")
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Msg msg = (Msg) o;
        return msgId == msg.msgId && Objects.equals(srcId, msg.srcId) && Objects.equals(dstId, msg.dstId) && Objects.equals(body, msg.body) && Objects.equals(timestamp, msg.timestamp) && Objects.equals(status, msg.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgId, srcId, dstId, body, timestamp, status);
    }
}
