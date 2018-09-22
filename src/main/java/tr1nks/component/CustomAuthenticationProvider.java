package tr1nks.component;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import tr1nks.domain.entity.UserEntity;
import tr1nks.service.domain.UserService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) {
        String email = auth.getName();
        String password = auth.getCredentials().toString();
        UserEntity userEntity = userService.getByEmail(email);
        if (null != userEntity && userEntity.isEnabled() && password.equals(userEntity.getPassword())) {
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority(userEntity.getRole().getRoleWithPrefix()));
            return new UsernamePasswordAuthenticationToken(userEntity.getUserUUID(), userEntity.getPassword(), roles);
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}