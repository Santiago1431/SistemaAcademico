package com.projeto.bd.SistemaAcademico.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define as regras de acesso para os endpoints.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults()) // Habilita o CORS (usamdo a CorsConfig)
                .csrf(csrf -> csrf.disable()) // Desativa CSRF para API REST

                .authorizeHttpRequests((authz) -> authz
                        // Permite requisições OPTIONS anonimamente (CRÍTICO para CORS/Basic Auth)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Regra: Todas as OUTRAS requisições devem ser autenticadas
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults()); // Habilita autenticação HTTP Basic (Janela de login)

        return http.build();
    }

    /**
     * Define as credenciais de usuário em memória (usuário e senha para o login).
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // Usuário de Teste: admin / senha123
        UserDetails user = User.withUsername("admin")
                .password(encoder.encode("senha123"))
                .roles("ADMIN") // Define o papel (role) do usuário
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
