package pe.edu.cibertec.proyecto_inventario.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.proyecto_inventario.model.Rol;
import pe.edu.cibertec.proyecto_inventario.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DetalleUsuarioService implements UserDetailsService {

    private final IUsuarioService usuarioService;

    public DetalleUsuarioService(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(">>> Buscando al usuario: " + username);

        Usuario usuario = usuarioService.findByNomusuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario '" + username + "' no fue encontrado."));


        System.out.println(">>> Usuario encontrado: " + usuario.getNomusuario() + " | Password (cifrado): " + usuario.getPassword());

        List<GrantedAuthority> authorities = getAuthorities(usuario.getRoles());

        return new User(
            usuario.getNomusuario(),
            usuario.getPassword(),
            usuario.getActivo(),
            true,
            true,
            true,
            authorities
        );
    }

    private List<GrantedAuthority> getAuthorities(Set<Rol> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNomrol()));
        }
        return authorities;
    }
}