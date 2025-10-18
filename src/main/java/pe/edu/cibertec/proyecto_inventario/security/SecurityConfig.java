package pe.edu.cibertec.proyecto_inventario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import pe.edu.cibertec.proyecto_inventario.service.DetalleUsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final IJwtService jwtService;
	private final DetalleUsuarioService detalleUsuarioService;

	public SecurityConfig(DetalleUsuarioService detalleUsuarioService, IJwtService jwtService) {
		this.jwtService = jwtService;
		this.detalleUsuarioService = detalleUsuarioService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/api/v1/auth/**").permitAll()
						// PRODUCTOS
						.requestMatchers(HttpMethod.GET, "/api/v1/product/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/v1/product/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.PUT, "/api/v1/product/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.DELETE, "/api/v1/product/**").hasRole("ADMIN")

		                // MARCAS
		                .requestMatchers(HttpMethod.GET, "/api/v1/brand/**").hasAnyRole("USER", "ADMIN")
		                .requestMatchers(HttpMethod.POST, "/api/v1/brand/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.PUT, "/api/v1/brand/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.DELETE, "/api/v1/brand/**").hasRole("ADMIN")

		                // CATEGORÍAS
		                .requestMatchers(HttpMethod.GET, "/api/v1/category/**").hasAnyRole("USER", "ADMIN")
		                .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.PUT, "/api/v1/category/**").hasRole("ADMIN")
		                .requestMatchers(HttpMethod.DELETE, "/api/v1/category/**").hasRole("ADMIN")
						
		                
						.anyRequest().authenticated()
						)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider()) 
				.addFilterBefore(filtroJwtAuth(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(detalleUsuarioService); 
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public FiltroJwtAuth filtroJwtAuth() {
		return new FiltroJwtAuth(jwtService);
	}
}