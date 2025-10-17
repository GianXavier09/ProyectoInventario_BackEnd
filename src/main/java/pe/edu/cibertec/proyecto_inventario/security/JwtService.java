package pe.edu.cibertec.proyecto_inventario.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JwtService implements IJwtService {

    private static final String SECRET_KEY = "clave_secreta_super_larga_y_segura_para_mi_proyecto_final_de_inventario";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Override
    public String generarTokens(Usuario usuario, List<GrantedAuthority> auths) {
        long expirationTime = 3_600_000; 

        return Jwts.builder()
                .id(usuario.getIdusuario().toString())
                .subject(usuario.getNomusuario())
                .claim("authorities",
                        auths.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(KEY)
                .compact();
    }

    @Override
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean tokenValido(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public String extraerToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    public void setAutenticacion(Claims claims) {
        List<String> authoritiesList = claims.get("authorities", List.class);
        
        List<SimpleGrantedAuthority> authorities = authoritiesList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities);
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}