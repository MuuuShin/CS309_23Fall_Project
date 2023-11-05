package cse.ooad.project.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;


/**
 * {@link Password} 用于表示账号密码信息的实体类。<br>
 * 属性列表：
 * <ul>
 *   <li>account: 账号。</li>
 *   <li>password: 密码，密码应该至少经过sha或hash加密。</li>
 * </ul>
 */

@Data
@Entity
@Table(name = "passwords", schema = "public", catalog = "cs309a")
public class Password {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "account")
    private String account;
    @Basic
    @Column(name = "password")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(account, password1.account) && Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, password);
    }
}
