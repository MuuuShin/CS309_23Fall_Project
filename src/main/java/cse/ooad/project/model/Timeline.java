package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;

@Getter
@Entity
@Table(name = "timelines", schema = "public", catalog = "cs309a")
public class Timeline {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "timeline_id")
  private int timelineId;
  @Basic
  @Column(name = "type")
  private String type;
  @Basic
  @Column(name = "begin_date1")
  private Date beginDate1;
  @Basic
  @Column(name = "end_date1")
  private Date endDate1;
  @Basic
  @Column(name = "begin_date2")
  private Date beginDate2;
  @Basic
  @Column(name = "end_date2")
  private Date endDate2;
  @Basic
  @Column(name = "begin_date3")
  private Date beginDate3;
  @Basic
  @Column(name = "end_date3")
  private Date endDate3;
  @Basic
  @Column(name = "begin_date4")
  private Date beginDate4;
  @Basic
  @Column(name = "end_date4")
  private Date endDate4;

  public void setTimelineId(int timelineId) {
    this.timelineId = timelineId;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setBeginDate1(Date beginDate1) {
    this.beginDate1 = beginDate1;
  }

  public void setEndDate1(Date endDate1) {
    this.endDate1 = endDate1;
  }

  public void setBeginDate2(Date beginDate2) {
    this.beginDate2 = beginDate2;
  }

  public void setEndDate2(Date endDate2) {
    this.endDate2 = endDate2;
  }

  public void setBeginDate3(Date beginDate3) {
    this.beginDate3 = beginDate3;
  }

  public void setEndDate3(Date endDate3) {
    this.endDate3 = endDate3;
  }

  public void setBeginDate4(Date beginDate4) {
    this.beginDate4 = beginDate4;
  }

  public void setEndDate4(Date endDate4) {
    this.endDate4 = endDate4;
  }
}
