package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

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
  @Column(name = "group_id")
  private Long groupId;

  @JsonIgnore
  @ManyToMany(mappedBy = "roomList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Group> groupList;

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
    return roomId == room.roomId && Objects.equals(name, room.name) && Objects.equals(type, room.type) && Objects.equals(intro, room.intro) && Objects.equals(status, room.status) && Objects.equals(floorId, room.floorId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roomId, name, type, intro, status, floorId);
  }
}
