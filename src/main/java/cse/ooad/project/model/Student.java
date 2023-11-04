package cse.ooad.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Objects;

/**
 * {@link  Student}用于表示学生信息的实体类，包括学生的基本信息和属性。<br>
 * <ul>
 *   <li>studentId: 学生ID，唯一标识学生。</li>
 *   <li>name: 学生姓名。</li>
 *   <li>intro: 学生介绍,可供学生自行修改。</li>
 *   <li>gender: 学生性别。</li>
 *   <li>groupId: 队伍ID。</li>
 *   <li>type: 学生类型(如是研究生还是博士生)。MASTER_MALE.MASTER_FEMALE,DOCTOR_MALE,DOCTOR_FEMALE 0,1,2,3</li>
 *   <li>awakeTime: 学生醒来时间。只存时分秒(Time)</li>
 *   <li>sleepTime: 学生睡觉时间。只存时分秒(Time)</li>
 *   <li>account: 学生账户。</li>
 *   <li>password: 学生密码，密码应该至少经过sha或hash加密。</li>
 *   <li>salt: 学生密码的盐。</li>
 *   <li>[映射]group: 学生所在队伍。</li>
 * </ul>
 */
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
    private Integer type;
    @Basic
    @Column(name = "awake_time")
    private Time awakeTime;
    @Basic
    @Column(name = "sleep_time")
    private Time sleepTime;
    @Basic
    @Column(name = "account")
    private String account;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "salt")
    private String salt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(name, student.name) && Objects.equals(intro, student.intro) && Objects.equals(gender, student.gender) && Objects.equals(groupId, student.groupId) && Objects.equals(type, student.type) && Objects.equals(awakeTime, student.awakeTime) && Objects.equals(sleepTime, student.sleepTime) && Objects.equals(account, student.account) && Objects.equals(password, student.password)  && Objects.equals(salt, student.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, intro, gender, groupId, type, awakeTime, sleepTime, account, password, salt);
    }
}
