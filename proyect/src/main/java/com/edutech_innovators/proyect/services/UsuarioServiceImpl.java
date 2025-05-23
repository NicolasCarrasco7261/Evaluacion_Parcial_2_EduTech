package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edutech_innovators.proyect.entities.Usuario;
import com.edutech_innovators.proyect.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByAll() {
        
        return (List<Usuario>) repository.findAll();
    }


    @Override
    @Transactional
     public Optional<Usuario> delete(Usuario unUsuario) {

        Optional<Usuario> usuarioOptional = repository.findById(unUsuario.getIdUsuario());
        usuarioOptional.ifPresent(usuarioDb->{
            repository.delete(unUsuario);
        });

        return usuarioOptional;

    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long idUsuario) {       
        return repository.findById(idUsuario);
    }


    @Override
    @Transactional
    public Usuario save(Usuario unUsuario) {
        
        return repository.save(unUsuario);
    }





}