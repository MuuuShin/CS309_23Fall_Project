package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Entity
@Table(name = "timelines", schema = "public", catalog = "cs309a")
public class Timeline {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "timeline_id")
    private Long timelineId;
    @Basic
    @Column(name = "type")
    private String type;
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

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBeginTime1(Timestamp beginTime1) {
        this.beginTime1 = beginTime1;
    }

    public void setEndTime1(Timestamp endTime1) {
        this.endTime1 = endTime1;
    }

    public void setBeginTime2(Timestamp beginTime2) {
        this.beginTime2 = beginTime2;
    }

    public void setEndTime2(Timestamp endTime2) {
        this.endTime2 = endTime2;
    }

    public void setBeginTime3(Timestamp beginTime3) {
        this.beginTime3 = beginTime3;
    }

    public void setEndTime3(Timestamp endTime3) {
        this.endTime3 = endTime3;
    }

    public void setBeginTime4(Timestamp beginTime4) {
        this.beginTime4 = beginTime4;
    }

    public void setEndTime4(Timestamp endTime4) {
        this.endTime4 = endTime4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timeline timeline = (Timeline) o;
        return timelineId == timeline.timelineId && Objects.equals(type, timeline.type) && Objects.equals(beginTime1, timeline.beginTime1) && Objects.equals(endTime1, timeline.endTime1) && Objects.equals(beginTime2, timeline.beginTime2) && Objects.equals(endTime2, timeline.endTime2) && Objects.equals(beginTime3, timeline.beginTime3) && Objects.equals(endTime3, timeline.endTime3) && Objects.equals(beginTime4, timeline.beginTime4) && Objects.equals(endTime4, timeline.endTime4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timelineId, type, beginTime1, endTime1, beginTime2, endTime2, beginTime3, endTime3, beginTime4, endTime4);
    }
}
