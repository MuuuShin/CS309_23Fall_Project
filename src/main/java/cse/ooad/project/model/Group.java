package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * {@link  Group}用于表示群组信息的实体类，包括群组的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>groupId: 群组ID，唯一标识群组。</li>
 *   <li>name: 群组名称。</li>
 *   <li>member1Id: 成员1的ID。</li>
 *   <li>member2Id: 成员2的ID。</li>
 *   <li>member3Id: 成员3的ID。</li>
 *   <li>member4Id: 成员4的ID。</li>
 *   <li>status: 群组状态，标识群是否已满，是否选择房间。</li>
 *   <li>leader: 群组领袖，值为1-4。不填默认1。</li>
 *   <li>roomId: 若选择了房间，给出房间的ID。</li>
 *   <li>[映射]roomStarList: 群组收藏的房间列表。</li>
 *   <li>[映射]room: 若选择了房间，给出房间。</li>
 * </ul>
 * 在加入新成员时，按照1-4顺序插入(若有空位)。<br>
 */
@Getter
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
  @Column(name = "member1_id")
  private Long member1Id;
  @Basic
  @Column(name = "member2_id")
  private Long member2Id;
  @Basic
  @Column(name = "member3_id")
  private Long member3Id;
  @Basic
  @Column(name = "member4_id")
  private Long member4Id;
  @Basic
  @Column(name = "status")
  private String status;
  @Basic
  @Column(name = "leader")
  private String leader;
  @Basic
  @Column(name = "room_id")
  private Long roomId;

  @JsonIgnore
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
          name = "group_stars",
          joinColumns = @JoinColumn(name = "group_id"),
          inverseJoinColumns = @JoinColumn(name = "room_id"))
  private List<Room> roomStarList;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "room_id", insertable = false, updatable = false)
  private Room room;

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMember1Id(Long member1Id) {
    this.member1Id = member1Id;
  }

  public void setMember2Id(Long member2Id) {
    this.member2Id = member2Id;
  }

  public void setMember3Id(Long member3Id) {
    this.member3Id = member3Id;
  }

  public void setMember4Id(Long member4Id) {
    this.member4Id = member4Id;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setLeader(String leader) {
    this.leader = leader;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return Objects.equals(groupId, group.groupId) && Objects.equals(name, group.name) && Objects.equals(member1Id, group.member1Id) && Objects.equals(member2Id, group.member2Id) && Objects.equals(member3Id, group.member3Id) && Objects.equals(member4Id, group.member4Id) && Objects.equals(status, group.status) && Objects.equals(leader, group.leader) && Objects.equals(roomId, group.roomId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, name, member1Id, member2Id, member3Id, member4Id, status, leader, roomId);
  }
}
