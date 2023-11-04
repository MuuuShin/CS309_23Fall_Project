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
 * {@link  Region}用于表示区划信息的实体类，包括区划的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>regionId: 区划ID，唯一标识区划。</li>
 *   <li>name: 区划名称，此字段暂无实际意义，保留字段。</li>
 *   <li>intro: 区划介绍。</li>
 *   <li>[映射]buildingList: 建筑列表。</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Exclude
    private List<Building> buildingList;

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
