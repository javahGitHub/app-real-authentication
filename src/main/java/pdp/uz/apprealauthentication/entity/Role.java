package pdp.uz.apprealauthentication.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import pdp.uz.apprealauthentication.entity.enums.RoleEnum;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role implements GrantedAuthority

{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @Override
    public String getAuthority() {
        return this.roleEnum.name();
    }
}