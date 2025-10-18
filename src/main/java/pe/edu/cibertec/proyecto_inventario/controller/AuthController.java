package pe.edu.cibertec.proyecto_inventario.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.cibertec.proyecto_inventario.dto.AuthRequest;
import pe.edu.cibertec.proyecto_inventario.dto.UsuarioSeguridadDto;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;
import pe.edu.cibertec.proyecto_inventario.security.IJwtService;
import pe.edu.cibertec.proyecto_inventario.service.IUsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.ArrayList; 
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final IUsuarioService usuarioService;
    private final IJwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, IUsuarioService usuarioService, IJwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioSeguridadDto> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Usuario usuario = usuarioService.findByNomusuario(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Error inesperado: Usuario no encontrado después de la autenticación."));

           
            List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

            String token = jwtService.generarTokens(usuario, authorities);

            UsuarioSeguridadDto responseDto = new UsuarioSeguridadDto(usuario.getIdusuario(), usuario.getNomusuario(), token, null);
            return ResponseEntity.ok(responseDto);
        } catch (BadCredentialsException e) {
            UsuarioSeguridadDto errorDto = new UsuarioSeguridadDto(null, null, null, "Error: Usuario o contraseña incorrectos.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDto);
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario){
    	try {
			Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
			return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
}

