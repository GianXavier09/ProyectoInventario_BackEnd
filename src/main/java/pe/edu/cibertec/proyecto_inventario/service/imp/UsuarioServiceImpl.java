package pe.edu.cibertec.proyecto_inventario.service.imp;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pe.edu.cibertec.proyecto_inventario.model.Rol;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;
import pe.edu.cibertec.proyecto_inventario.repository.RolRepository;
import pe.edu.cibertec.proyecto_inventario.repository.UsuarioRepository;
import pe.edu.cibertec.proyecto_inventario.service.IUsuarioService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,RolRepository rolRepository,PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.rolRepository = rolRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override 
    public Optional<Usuario> findByNomusuario(String nomUsuario) {
        return usuarioRepository.findByNomusuario(nomUsuario);
    }
	
	@Override
	public Usuario registrarUsuario(Usuario usuario) {
		
		if(usuarioRepository.findByNomusuario(usuario.getNomusuario()).isPresent()) {
			throw new RuntimeException("El nombre de ya esta en uso");
		}
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		//ASIGNAR rol de USER
		Rol rolUsuario = rolRepository.findByNomrol("USER")
				.orElseThrow(() -> new RuntimeException("Error: Rol 'USER' no encontrado"));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolUsuario);
		usuario.setRoles(roles);
		
		//establecer el usuario como activo
		usuario.setActivo(true);
		
		return usuarioRepository.save(usuario);
	}
    
    
}