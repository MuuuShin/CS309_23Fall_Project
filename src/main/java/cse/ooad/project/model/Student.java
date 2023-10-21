package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Objects;

@Data
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
    @Column(name = "awake_time")
    private Time awakeTime;
    @Basic
    @Column(name = "sleep_time")
    private String sleepTime;
    @Basic
    @Column(name = "account")
    private String account;
    @Basic
    @Column(name = "password")
    private String password;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(name, student.name) && Objects.equals(intro, student.intro) && Objects.equals(gender, student.gender) && Objects.equals(groupId, student.groupId) && Objects.equals(type, student.type) && Objects.equals(awakeTime, student.awakeTime) && Objects.equals(sleepTime, student.sleepTime) && Objects.equals(account, student.account) && Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, intro, gender, groupId, type, awakeTime, sleepTime, account, password);
    }
}
