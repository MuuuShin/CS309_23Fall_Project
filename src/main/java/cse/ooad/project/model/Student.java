package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "students", schema = "public", catalog = "cs309a")
public class Student {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "student_id")
  private Long studentId;
  @Basic
  @Column(name = "name")
  private String name;
  @Basic
  @Column(name = "intro")
  private String intro;
  @Basic
  @Column(name = "gender")
  private Short gender;
  @Basic
  @Column(name = "group_id")
  private Long groupId;
  @Basic
  @Column(name = "type")
  private String type;
  @Basic
  @Column(name = "tag1")
  private String tag1;
  @Basic
  @Column(name = "tag2")
  private String tag2;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "group_id", insertable = false, updatable = false)
  private Group group;

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

  public void setGender(Short gender) {
    this.gender = gender;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setTag1(String tag1) {
    this.tag1 = tag1;
  }

  public void setTag2(String tag2) {
    this.tag2 = tag2;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return studentId == student.studentId && Objects.equals(name, student.name) && Objects.equals(intro, student.intro) && Objects.equals(gender, student.gender) && Objects.equals(groupId, student.groupId) && Objects.equals(type, student.type) && Objects.equals(tag1, student.tag1) && Objects.equals(tag2, student.tag2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(studentId, name, intro, gender, groupId, type, tag1, tag2);
  }
}
