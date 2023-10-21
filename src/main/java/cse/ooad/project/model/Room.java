package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;


/**
 * {@link  Room}用于表示房间信息的实体类，包括房间的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>roomId: 房间ID，唯一标识房间。</li>
 *   <li>name: 房间名称。</li>
 *   <li>type: 房间类型，如是给博士生的还是硕士生的，是几人间。</li>
 *   <li>intro: 房间介绍。</li>
 *   <li>status: 房间状态，如是否被选择。</li>
 *   <li>floorId: 所属楼层ID。</li>
 *   <li>commentBaseId: 元评论ID，在评论中此ID视作房间本身。 </li>
 *   <li>[映射]groupStarList: 收藏此房间的群组列表。</li>
 *   <li>[映射]floor: 所属楼层。</li>
 *   <li>[映射]group: 若被选择，给出选择的群组。</li>
 *   <li>[映射]commentBase: 元评论。</li>
 * </ul>
 * @see Comment
 */
@Getter
@Entity
@Table(name = "rooms", schema = "public", catalog = "cs309a")
public class Room {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "intro")
    private String intro;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "floor_id")
    private Long floorId;
    @Basic
    @Column(name = "comment_base_id")
    private Long commentBaseId;

    @JsonIgnore
    @ManyToMany(mappedBy = "roomStarList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Group> groupStarList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "floor_id", insertable = false, updatable = false)
    private Floor floor;

    @JsonIgnore
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Group group;

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId) && Objects.equals(name, room.name) && Objects.equals(type, room.type) && Objects.equals(intro, room.intro) && Objects.equals(status, room.status) && Objects.equals(floorId, room.floorId) && Objects.equals(commentBaseId, room.commentBaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, name, type, intro, status, floorId, commentBaseId);
    }
}
