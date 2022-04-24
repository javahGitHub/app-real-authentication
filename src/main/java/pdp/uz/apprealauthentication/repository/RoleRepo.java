package pdp.uz.apprealauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pdp.uz.apprealauthentication.entity.Role;
import pdp.uz.apprealauthentication.entity.enums.RoleEnum;

import java.util.Optional;


public interface RoleRepo extends JpaRepository<Role,Integer> {

    Role findByRoleEnum(RoleEnum roleEnum);
}
