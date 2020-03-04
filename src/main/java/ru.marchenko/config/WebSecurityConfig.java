package ru.marchenko.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.marchenko.model.entity.User;
import ru.marchenko.model.repository.UsersRepo;

/**
 * @author Vladislav Marchenko
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/", "/static/**", "/templates/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UsersRepo usersRepo) {
        return map -> {
            String id = (String) map.get("sub");

            User user = usersRepo.findById(id).orElseGet(() -> {
                User newUser = new User();

                newUser.setUserID(id);
                newUser.setEmail((String) map.get("email"));
                newUser.setUserName((String) map.get("name"));
                return newUser;
            });

            return usersRepo.save(user);
        };
    }
}
