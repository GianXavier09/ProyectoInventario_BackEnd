package pe.edu.cibertec.proyecto_inventario.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;


public interface IJwtService {

	    String generarTokens(Usuario usuario, List<GrantedAuthority> auths);

	    Claims parseClaims(String token);

	    boolean tokenValido(String token);

	    String extraerToken(HttpServletRequest request);

	    void setAutenticacion(Claims claims);
	}
