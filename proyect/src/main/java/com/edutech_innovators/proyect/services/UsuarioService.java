package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;
import com.edutech_innovators.proyect.entities.Usuario;

public interface UsuarioService {


    List<Usuario> findByAll();
    
    Optional<Usuario> findById(Long id);

    Usuario save (Usuario unUsuario);

    Optional<Usuario> delete(Usuario unUsuario);
    

}

