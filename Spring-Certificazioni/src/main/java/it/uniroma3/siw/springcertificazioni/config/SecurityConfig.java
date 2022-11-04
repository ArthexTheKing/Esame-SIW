package it.uniroma3.siw.springcertificazioni.config;

import static it.uniroma3.siw.springcertificazioni.model.enumeration.Ruolo.SEGRETERIA;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            /* Configurazione Permessi */
            .antMatchers(GET,  "/", "/index", "/login", "/images/**", "/layout/**").permitAll()
            .antMatchers(POST, "/login").permitAll()
            .antMatchers(GET, "/admin/**").hasAnyAuthority(SEGRETERIA.toString())
            .antMatchers(POST, "/admin/**").hasAnyAuthority(SEGRETERIA.toString())
            .anyRequest().authenticated()
            .and()
            /* Configurazione Login */
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/home")
            .failureUrl("/index?error=true")
            .and()
            /* Configurazione Logout */
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/index")
            .invalidateHttpSession(true)
            .clearAuthentication(true);
        return http.build();
    }

    @Bean
    public UserDetailsManager jdbcConfig(DataSource dataSource) {
        JdbcUserDetailsManager jdbc = new JdbcUserDetailsManager(dataSource);
        jdbc.setAuthoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?");
        jdbc.setUsersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
        return jdbc;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
