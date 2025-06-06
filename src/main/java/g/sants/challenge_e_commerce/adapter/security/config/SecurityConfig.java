package g.sants.challenge_e_commerce.adapter.security.config;

import g.sants.challenge_e_commerce.adapter.security.methods.SecurityCategory;
import g.sants.challenge_e_commerce.application.port.filters.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter){
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain
            (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, SecurityCategory.ITEM_P1).hasRole(SecurityCategory.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT,SecurityCategory.ITEM_P1).hasRole(SecurityCategory.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE,SecurityCategory.ITEM_P1).hasRole(SecurityCategory.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET,"/users").hasRole(SecurityCategory.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE,"/users").hasRole(SecurityCategory.ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT,"/users/{id}").hasRole(SecurityCategory.ADMIN_ROLE)
                        .anyRequest().fullyAuthenticated())

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
