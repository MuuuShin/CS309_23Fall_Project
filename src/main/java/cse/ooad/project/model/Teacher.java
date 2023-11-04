package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

/**
 * {@link Teacher} 用于表示教师信息的实体类，包括教师的基本信息和属性。<br>
 * 属性列表：
 * <ul>
 *   <li>teacherId: 教师ID，唯一标识教师。</li>
 *   <li>name: 教师姓名。</li>
 *   <li>permission: 教师权限，暂定分为拥有全部权限和只能增查权限两种。</li>
 *   <li>account: 教师账号。</li>
 *   <li>password: 教师密码，密码应该至少经过sha或hash加密。</li>
 *   <li>salt: 教师密码的盐。</li>
 * </ul>
 */
@Data
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
    @Basic
    @Column(name = "salt")
    private String salt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(name, teacher.name) && Objects.equals(permission, teacher.permission) && Objects.equals(account, teacher.account) && Objects.equals(password, teacher.password) && Objects.equals(salt, teacher.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, name, permission, account, password, salt);
    }
}
