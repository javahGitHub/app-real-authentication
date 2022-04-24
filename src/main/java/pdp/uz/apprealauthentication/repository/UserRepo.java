package pdp.uz.apprealauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pdp.uz.apprealauthentication.entity.User;

import java.util.Optional;
import java.util.UUID;


public interface UserRepo extends JpaRepository<User, UUID> {
     boolean existsByEmail(String email);
     boolean existsByPassword(String password);


    Optional<User> findByEmailAndEmailCode(String email, String emailCode);
    Optional<User> findByEmail(String email);

}
