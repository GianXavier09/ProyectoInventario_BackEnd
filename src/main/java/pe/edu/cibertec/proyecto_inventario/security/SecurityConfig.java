package pe.edu.cibertec.proyecto_inventario.security;

import java.util.Arrays; // <-- IMPORTACIÓN NUEVA

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
import org.springframework.web.cors.CorsConfiguration; // <-- IMPORTACIÓN NUEVA
import org.springframework.web.cors.CorsConfigurationSource; // <-- IMPORTACIÓN NUEVA
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <-- IMPORTACIÓN NUEVA

import static org.springframework.security.config.Customizer.withDefaults; // <-- IMPORTACIÓN NUEVA

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
				// 1. AÑADIR LA CONFIGURACIÓN DE CORS
				.cors(withDefaults()) // Esto le dice a Spring Security que use el Bean 'corsConfigurationSource'
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

	// 2. CREAR EL BEAN CON LA CONFIGURACIÓN DE CORS
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		// Orígenes permitidos (¡tu frontend de Angular!)
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		// Métodos permitidos (GET, POST, y MUY IMPORTANTE: OPTIONS para el preflight)
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		// Cabeceras permitidas (MUY IMPORTANTE: Authorization para tu token)
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/v1/**", configuration); // Aplica esta configuración a todas las rutas de tu API
		return source;
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
