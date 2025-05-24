package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;
import com.edutech_innovators.proyect.entities.Alumno;

public interface AlumnoService {


    List<Alumno> findByAll();
    
    Optional<Alumno> findById(Long id);

    Alumno save (Alumno unAlumno);

    Optional<Alumno> delete(Alumno unAlumno);
    

}