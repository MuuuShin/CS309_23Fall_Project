package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

/**
 * {@link  Group}用于表示群组信息的实体类，包括群组的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>groupId: 群组ID，唯一标识群组。</li>
 *   <li>name: 群组名称。</li>
 *   <li>status: [保留]群组状态，标识群是否已满，是否选择房间。</li>
 *   <li>leader: 群组领袖，值为队长ID。</li>
 *   <li>roomId: 若选择了房间，给出房间的ID。没有则为-1。</li>
 *   <li>[映射]roomStarList: 群组收藏的房间列表。</li>
 *   <li>[映射]room: 若选择了房间，给出房间。</li>
 *   <li>[映射]memberList: 队伍成员。</li>
 * </ul>
 * 在加入新成员时，按照1-4顺序插入(若有空位)。<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups", schema = "public", catalog = "cs309a")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "group_id")
    private Long groupId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "leader")
    private Long leader;
    @Basic
    @Column(name = "room_id")
    private Long roomId;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "group_stars",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    @Exclude
    private List<Room> roomStarList;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    @Exclude
    private Room room;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    @Exclude
    private List<Student> memberList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId) && Objects.equals(name, group.name) && Objects.equals(status, group.status) && Objects.equals(leader, group.leader) && Objects.equals(roomId, group.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, name, status, leader, roomId);
    }
}
