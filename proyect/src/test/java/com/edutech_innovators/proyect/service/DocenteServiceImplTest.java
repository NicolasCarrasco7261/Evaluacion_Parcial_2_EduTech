package com.edutech_innovators.proyect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.edutech_innovators.proyect.entities.Docente;
import com.edutech_innovators.proyect.repository.DocenteRepository;
import com.edutech_innovators.proyect.services.DocenteServiceImpl;



public class DocenteServiceImplTest {

    
    @InjectMocks
    private DocenteServiceImpl service;

    @Mock
    private DocenteRepository repository;

    List <Docente> list = new ArrayList<Docente>();


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeDocente();
    }


    // Test Método findByAll
    @Test
    public void findByAllTest(){

        when(repository.findAll()).thenReturn(list);

        List <Docente> response = service.findByAll();

        // Esperamos que traiga los tres usuarios que tenemos en la lista
        assertEquals(3, response.size());

        verify(repository, times(1)).findAll();

    }



    public void chargeDocente(){

        Docente Doc1 = new Docente(Long.valueOf(1), "mi@hotmail.com", 10, "Java");
        Docente Doc2 = new Docente(Long.valueOf(2), "mi@hotmail.com", 5, "Java");
        Docente Doc3 = new Docente(Long.valueOf(3), "mi@hotmail.com", 6, "Java");

        list.add(Doc1);
        list.add(Doc2);
        list.add(Doc3);

    }




    // Test Método findById
    @Test
    public void findByIdTest() {

        Docente docente = new Docente(1L, "doc1@hotmail.com", 10, "Java");
        when(repository.findById(1L)).thenReturn(Optional.of(docente));


        Optional<Docente> result = service.findById(1L);


        assertTrue(result.isPresent());
        assertEquals("doc1@hotmail.com", result.get().getCorreo());
        assertEquals(10, result.get().getExperiencia());
        assertEquals("Java", result.get().getCatedra());
        verify(repository, times(1)).findById(1L);

}


    // Test Método save
    @Test
    public void saveDocenteTest() {

        Docente docenteToSave = new Docente(10, "nuevo@correo.com", 4, "Python");
        Docente docenteSaved = new Docente(10L, "nuevo@correo.com", 4, "Python");

        when(repository.save(docenteToSave)).thenReturn(docenteSaved);

        Docente result = service.save(docenteToSave);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("nuevo@correo.com", result.getCorreo());
        assertEquals(4, result.getExperiencia());
        assertEquals("Python", result.getCatedra());
        verify(repository, times(1)).save(docenteToSave);

    }



    // Test Método delete
    @Test
    public void deleteDocenteSuccessTest() {
        
        Docente docente = new Docente(1L, "mi@correo.com", 10, "Java");
        when(repository.findById(1L)).thenReturn(Optional.of(docente));
        doNothing().when(repository).delete(docente);

        
        Optional<Docente> result = service.delete(docente);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(docente);
    }


}
