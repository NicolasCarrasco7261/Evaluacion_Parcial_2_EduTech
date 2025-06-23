package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edutech_innovators.proyect.entities.Alumno;
import com.edutech_innovators.proyect.repository.AlumnoRepository;

@Service
public class AlumnoServiceImpl implements AlumnoService {


    @Autowired
    private AlumnoRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Alumno> findByAll() {
        
        return (List<Alumno>) repository.findAll();
    }


    @Override
    @Transactional
    public Optional<Alumno> delete(Alumno unAlumno) {
        
        Optional<Alumno> alumnoOptional = repository.findById(unAlumno.getId());
        alumnoOptional.ifPresent(alumnoDb->{
            repository.delete(unAlumno);
        });

        return alumnoOptional;
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Alumno> findById(Long id) {       
        return repository.findById(id);
    }


    @Override
    @Transactional
    public Alumno save(Alumno unAlumno) {
        
        return repository.save(unAlumno);
    }




    

}
