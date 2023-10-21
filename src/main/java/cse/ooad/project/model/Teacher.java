package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "teachers", schema = "public", catalog = "cs309a")
public class Teacher {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "teacher_id")
    private Long teacherId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "permission")
    private String permission;
    @Basic
    @Column(name = "account")
    private String account;
    @Basic
    @Column(name = "password")
    private String password;

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(name, teacher.name) && Objects.equals(permission, teacher.permission) && Objects.equals(account, teacher.account) && Objects.equals(password, teacher.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, name, permission, account, password);
    }
}
