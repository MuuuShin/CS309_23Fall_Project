package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "regions", schema = "public", catalog = "cs309a")
public class Region {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "region_id")
  private Long regionId;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "intro")
  private String intro;

  @JsonIgnore
  @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Building> buildingList;

  public void setRegionId(Long regionId) {
    this.regionId = regionId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Region region = (Region) o;
    return Objects.equals(regionId, region.regionId) && Objects.equals(name, region.name) && Objects.equals(intro, region.intro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(regionId, name, intro);
  }
}
