package tr1nks.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import tr1nks.component.CustomAuthenticationProvider;
import tr1nks.domain.entity.enums.SiteRolesEnum;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/properties/rootAdmin.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static String HAS_ROLE = "hasRole('";
    private static String HAS_ANY_ROLE = "hasAnyRole('";
    private static String COMMA = "', '";
    private static String TAIL = "')";
    @Resource
    private DataSource dataSource;
    @Value("${admin.root.enabled}")
    private boolean enabledRootAdmin;
    @Value("${admin.root.email}")
    private String emailRootAdmin;
    @Value("${admin.root.password}")
    private String passwordRootAdmin;
    @Resource
    private CustomAuthenticationProvider customAuthProvider;

    /**
     * create a 'hasRole('.....')' string
     */
    private static String hasAnyRole(SiteRolesEnum... roles) {
        StringBuilder builder = new StringBuilder(HAS_ANY_ROLE);
        Arrays.stream(roles).forEach(r -> builder.append(r.getRoleWithPrefix()).append(COMMA));
        return builder.replace(builder.lastIndexOf(COMMA), builder.lastIndexOf(COMMA) + 4, TAIL).toString();
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
//        security.authorizeRequests()
//                .antMatchers(CommonController.URL_BASE + "**").access(hasAnyRole(SiteRolesEnum.ADMIN, SiteRolesEnum.USER))
//                .antMatchers(CrudController.URL_BASE + "**").access(hasAnyRole(SiteRolesEnum.ADMIN))
//                .antMatchers(ParseController.URL_BASE + "**").access(hasAnyRole(SiteRolesEnum.ADMIN, SiteRolesEnum.USER))
//                .antMatchers(Controller.LOGIN_URL + "/**").permitAll()
//
//                .and().formLogin()
//                .loginPage(Controller.LOGIN_URL)
//                .defaultSuccessUrl(CommonController.URL_BASE + "main", false)
//                .failureUrl(Controller.LOGIN_URL + "?error=true")
//                .and().logout()
//                .logoutUrl("/logout").logoutSuccessUrl(Controller.LOGIN_URL + "?logout")
//                .and().csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        if (enabledRootAdmin) {
            builder.inMemoryAuthentication()
                    .withUser(emailRootAdmin)
                    .password(passwordRootAdmin)
                    .roles(SiteRolesEnum.ADMIN.getRole(), SiteRolesEnum.USER.getRole());
        }
        builder.authenticationProvider(customAuthProvider);
    }
}
