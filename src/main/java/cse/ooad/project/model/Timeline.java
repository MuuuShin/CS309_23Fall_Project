package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;
import lombok.NoArgsConstructor;

/**
 * {@link Timeline} 用于表示时间线信息的实体类，包括时间线的基本信息和属性。这个类用来标明不同type的学生选房的四个阶段时间段。<br>
 * 属性列表：
 * <ul>
 *   <li>timelineId: 时间线ID，唯一标识时间线。</li>
 *   <li>type: 时间线类型，这个类型应该与学生type对应。MASTER_MALE.MASTER_FEMALE,DOCTOR_MALE,DOCTOR_FEMALE 0,1,2,3</li>
 *   <li>beginTime1: 时间线开始时间1。这里都是时间戳</li>
 *   <li>endTime1: 时间线结束时间1。</li>
 *   <li>beginTime2: 时间线开始时间2。</li>
 *   <li>endTime2: 时间线结束时间2。</li>
 *   <li>beginTime3: 时间线开始时间3。</li>
 *   <li>endTime3: 时间线结束时间3。</li>
 *   <li>beginTime4: 时间线开始时间4。</li>
 *   <li>endTime4: 时间线结束时间4。</li>
 * </ul>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timelines", schema = "public", catalog = "cs309a")
public class Timeline {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "timeline_id")
    private Long timelineId;
    @Basic
    @Column(name = "type")
    private Integer type;
    @Basic
    @Column(name = "begin_time1")
    private Timestamp beginTime1;
    @Basic
    @Column(name = "end_time1")
    private Timestamp endTime1;
    @Basic
    @Column(name = "begin_time2")
    private Timestamp beginTime2;
    @Basic
    @Column(name = "end_time2")
    private Timestamp endTime2;
    @Basic
    @Column(name = "begin_time3")
    private Timestamp beginTime3;
    @Basic
    @Column(name = "end_time3")
    private Timestamp endTime3;
    @Basic
    @Column(name = "begin_time4")
    private Timestamp beginTime4;
    @Basic
    @Column(name = "end_time4")
    private Timestamp endTime4;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeline timeline = (Timeline) o;
        return Objects.equals(timelineId, timeline.timelineId) && Objects.equals(type, timeline.type) && Objects.equals(beginTime1, timeline.beginTime1) && Objects.equals(endTime1, timeline.endTime1) && Objects.equals(beginTime2, timeline.beginTime2) && Objects.equals(endTime2, timeline.endTime2) && Objects.equals(beginTime3, timeline.beginTime3) && Objects.equals(endTime3, timeline.endTime3) && Objects.equals(beginTime4, timeline.beginTime4) && Objects.equals(endTime4, timeline.endTime4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timelineId, type, beginTime1, endTime1, beginTime2, endTime2, beginTime3, endTime3, beginTime4, endTime4);
    }
}
