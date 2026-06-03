package bf.asce.plateformeasce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // On désactive la protection CSRF (inutile pour une API REST sans session)
            .csrf(csrf -> csrf.disable())

            // On ne crée pas de session : chaque requête s'authentifie avec son token
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Les règles d'accès aux URL
            .authorizeHttpRequests(auth -> auth
                // Pour l'instant, on autorise TOUT le monde (le temps de tester)
                .anyRequest().authenticated()
            )

            // On active la vérification des tokens JWT venant de Keycloak
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        return http.build();
    }
}