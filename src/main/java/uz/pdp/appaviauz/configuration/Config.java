package uz.pdp.appaviauz.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.appaviauz.entity.enums.Authority;
import uz.pdp.appaviauz.filter.MyFilter;
import uz.pdp.appaviauz.repository.UserRepository;
import uz.pdp.appaviauz.util.Path;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
public class Config {
    private final UserRepository userRepository;
    private final MyFilter myFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElse(null);
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());

        return provider;
    }

    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(Path.OPEN_PAGES)
                                .permitAll()
                                .requestMatchers("/api/sw/**")
                                .hasAuthority(Authority.CRUD_ENTITY.name())
                                .requestMatchers(Path.API_VERSION_ALL).authenticated()
                                .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public Base64 base64() {
        return new Base64();
    }

}
