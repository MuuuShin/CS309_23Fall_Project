package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.List;
import java.util.Objects;

/**
 * {@link  Building}用于表示楼栋信息的实体类，包括楼栋的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>buildingId: 楼栋ID，唯一标识建筑。</li>
 *   <li>name: 楼栋名称。</li>
 *   <li>intro: 楼栋介绍，此字段暂无实际意义，保留字段。</li>
 *   <li>regionId: 所属区域的ID。</li>
 *   <li>[映射]region: 所属区域。</li>
 *   <li>[映射]floorList: 楼层列表。</li>
 * </ul>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    /* 映射实体 */

    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;

    @Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Floor> floorList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(buildingId, building.buildingId) && Objects.equals(name, building.name) && Objects.equals(intro, building.intro) && Objects.equals(regionId, building.regionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingId, name, intro, regionId);
    }
}
