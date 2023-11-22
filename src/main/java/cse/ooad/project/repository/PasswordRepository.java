package cse.ooad.project.repository;

import cse.ooad.project.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {

    interface PasswordProjection {

        String getPassword();
    }

    PasswordProjection findPasswordByAccount(String account);


}
