package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "buildings", schema = "public", catalog = "cs309a")
public class Building {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "building_id")
  private Long buildingId;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "intro")
  private String intro;
  @Basic
  @Column(name = "region_id")
  private Long regionId;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "region_id", insertable = false, updatable = false)
  private Region region;

  @JsonIgnore
  @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Building> floorList;

  public void setBuildingId(Long buildingId) {
    this.buildingId = buildingId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public void setRegionId(Long regionId) {
    this.regionId = regionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Building building = (Building) o;
    return buildingId == building.buildingId && Objects.equals(name, building.name) && Objects.equals(intro, building.intro) && Objects.equals(regionId, building.regionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingId, name, intro, regionId);
  }
}
