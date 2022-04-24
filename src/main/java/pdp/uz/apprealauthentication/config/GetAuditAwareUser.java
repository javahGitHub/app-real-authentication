package pdp.uz.apprealauthentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pdp.uz.apprealauthentication.entity.User;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing
public class GetAuditAwareUser {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    AuditorAware<UUID> auditorAware(){
        return new AuditorAware<UUID>() {

            @Override
            public Optional<UUID> getCurrentAuditor() {
                if(authentication!=null
                        &&authentication.isAuthenticated()
                        &&!authentication.getPrincipal().equals("anonymousUser")){
                   User user= (User) authentication.getPrincipal();
                    return Optional.of(user.getId());
                }
                System.out.println("Optional is empty");
                return Optional.empty();
            }

        };
    }
}
