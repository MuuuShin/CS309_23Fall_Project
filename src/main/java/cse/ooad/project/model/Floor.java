package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.List;
import java.util.Objects;

/**
 * {@link  Floor}用于表示楼层信息的实体类，包括楼层的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>floorId: 楼层ID，唯一标识楼层。</li>
 *   <li>name: 楼层名称。</li>
 *   <li>intro: 楼层介绍，此字段暂无实际意义，保留字段。</li>
 *   <li>buildingId: 所属建筑的ID。</li>
 *   <li>[映射]building: 所属建筑。</li>
 *   <li>[映射]roomList: 房间列表。</li>
 * </ul>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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

    /* 映射实体 */

    @ManyToOne
    @JoinColumn(name = "building_id", insertable = false, updatable = false)
    private Building building;

    @Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Room> roomList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return Objects.equals(floorId, floor.floorId) && Objects.equals(name, floor.name) && Objects.equals(intro, floor.intro) && Objects.equals(buildingId, floor.buildingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorId, name, intro, buildingId);
    }
}
