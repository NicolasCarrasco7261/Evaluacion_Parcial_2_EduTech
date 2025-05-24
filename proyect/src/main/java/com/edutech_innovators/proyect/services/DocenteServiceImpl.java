package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edutech_innovators.proyect.entities.Docente;
import com.edutech_innovators.proyect.repository.DocenteRepository;

@Service
public class DocenteServiceImpl implements DocenteService{


    @Autowired
    private DocenteRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Docente> findByAll() {
        
        return (List<Docente>) repository.findAll();
    }



    @Override
    @Transactional
    public Optional<Docente> delete(Docente unDocente) {
        
        Optional<Docente> docenteOptional = repository.findById(unDocente.getId());
        docenteOptional.ifPresent(docenteDb->{
            repository.delete(unDocente);
        });

        return docenteOptional;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Docente> findById(Long id) {       
        return repository.findById(id);
    }


    @Override
    @Transactional
    public Docente save(Docente unDocente) {
        
        return repository.save(unDocente);
    }




}
