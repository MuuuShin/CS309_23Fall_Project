package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "floors", schema = "public", catalog = "cs309a")
public class Floor {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "floor_id")
  private Long floorId;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "intro")
  private String intro;
  @Basic
  @Column(name = "building_id")
  private Long buildingId;

  @ManyToOne
  @JsonIgnore
  @JoinColumn(name = "building_id", insertable = false, updatable = false)
  private Building building;

  @JsonIgnore
  @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Room> roomList;

  public void setFloorId(Long floorId) {
    this.floorId = floorId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public void setBuildingId(Long buildingId) {
    this.buildingId = buildingId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Floor floor = (Floor) o;
    return floorId == floor.floorId && Objects.equals(name, floor.name) && Objects.equals(intro, floor.intro) && Objects.equals(buildingId, floor.buildingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(floorId, name, intro, buildingId);
  }
}
