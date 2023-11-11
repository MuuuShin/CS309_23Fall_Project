package cse.ooad.project.model;

import cse.ooad.project.utils.MessageStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * {@link Msg} 用于表示消息的实体类，包括消息的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>msgId: 消息的唯一标识。</li>
 *   <li>srcId: 消息发送者的标识。如果为0则为系统消息(System)</li>
 *   <li>dstId: 消息接收者的标识。</li>
 *   <li>body: 消息内容。</li>
 *   <li>timestamp: 消息时间戳，表示消息发送的时间。</li>
 *   <li>status: 消息是否已读的状态。枚举类参见{@link MessageStatus}</li>
 * </ul>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "msgs", schema = "public", catalog = "cs309a")
public class                                                               Msg {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "msg_id")
    private Long msgId;
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
        return Objects.equals(msgId, msg.msgId) && Objects.equals(srcId, msg.srcId) && Objects.equals(dstId, msg.dstId) && Objects.equals(body, msg.body) && Objects.equals(timestamp, msg.timestamp) && Objects.equals(status, msg.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgId, srcId, dstId, body, timestamp, status);
    }
}
