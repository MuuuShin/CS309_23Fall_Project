package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.List;
import java.util.Objects;

/**
 * {@link  Group}用于表示群组信息的实体类，包括群组的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>groupId: 群组ID，唯一标识群组。</li>
 *   <li>name: 群组名称。</li>
 *   <li>leader: 群组领袖，值为队长ID。</li>
 *   <li>roomId: 若选择了房间，给出房间的ID。没有则为-1。</li>
 *   <li>[映射]roomStarList: 群组收藏的房间列表。</li>
 *   <li>[映射]room: 若选择了房间，给出房间。</li>
 *   <li>[映射]memberList: 队伍成员。</li>
 * </ul>
 * 在加入新成员时，按照1-4顺序插入(若有空位)。<br>
 */
@Getter
@Setter
@ToString
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
    @Column(name = "leader")
    private Long leader;
    @Basic
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "intro")
    private String intro;
    /* 映射实体 */

    @Exclude
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "group_stars",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> roomStarList;

    @Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false, nullable = true)
    private Room room;

    @Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Student> memberList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(groupId, group.groupId) && Objects.equals(name, group.name) && Objects.equals(leader, group.leader) && Objects.equals(roomId, group.roomId) && Objects.equals(intro, group.intro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, name, leader, roomId, intro);
    }
}
