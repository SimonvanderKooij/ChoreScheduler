package nl.miwnn.ch17.svdkooij.chorescheduler.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Simon van der Kooij
 * configure the security for the Chore Schedule application
 */

@Configuration
@EnableWebSecurity
public class ChoreScheduleSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(ChoreScheduleSecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "chore/all", "schedule/all", "familymember/all").permitAll()
                        .requestMatchers("/schedule/detail/**", "familymember/detail/**").permitAll()
                        .requestMatchers("/css/**", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .logout((logout) -> logout.logoutSuccessUrl("/"));


        return httpSecurity.build();

    }
}
