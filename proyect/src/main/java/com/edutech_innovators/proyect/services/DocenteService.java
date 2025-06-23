package com.edutech_innovators.proyect.services;

import java.util.List;
import java.util.Optional;
import com.edutech_innovators.proyect.entities.Docente;

public interface DocenteService {



    List<Docente> findByAll();

    Optional<Docente> findById(Long id);

    Docente save (Docente unDocente);

    Optional<Docente> delete(Docente unDocente);


}


