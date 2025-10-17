package pe.edu.cibertec.proyecto_inventario.service;

import pe.edu.cibertec.proyecto_inventario.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findByNomusuario(String nomUsuario);
    
    Usuario registrarUsuario(Usuario usuario);
}