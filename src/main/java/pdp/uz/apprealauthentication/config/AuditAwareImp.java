package pdp.uz.apprealauthentication.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pdp.uz.apprealauthentication.entity.User;

import javax.jws.soap.SOAPBinding;
import java.util.Optional;
import java.util.UUID;

public class AuditAwareImp implements AuditorAware<UUID> {

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null
           &&authentication.isAuthenticated()
           &&!authentication.getPrincipal().equals("anonymousUser")){
            return Optional.of(((User) authentication.getPrincipal()).getId());
        }
        System.out.println("Optional is empty");
        return Optional.empty();
    }
}
