package com.edutech_innovators.proyect.service;

import com.edutech_innovators.proyect.entities.Alumno;
import com.edutech_innovators.proyect.repository.AlumnoRepository;
import com.edutech_innovators.proyect.services.AlumnoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlumnoServiceImplTest {

    @InjectMocks
    private AlumnoServiceImpl alumnoServiceImpl;

    @Mock
    private AlumnoRepository alumnoRepository;

    List <Alumno> list = new ArrayList<Alumno>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        this.chargeAlumno();
    }
    
    //Test para el metodo findByAll
    @Test
    @DisplayName("Debería retornar una lista con todos los alumnos simulados")
    public void findByAllTest(){
        when(alumnoRepository.findAll()).thenReturn(list);
        List<Alumno> response = alumnoServiceImpl.findByAll();
        //Esperamos que traiga los 3 alumnos que tenemos en la lista que simula la base de datos.
        assertEquals(3, response.size());

        //verificamos que se llame al metodo findAll una vez.
        verify(alumnoRepository, times(1)).findAll();
    }

    public void chargeAlumno(){
        Alumno alu1= new Alumno(1L, "carc.lopez@duocuc.cl", 2, "FullStack");
        Alumno alu2 = new Alumno(2L, "cami.pobleteg@duocuc.cl", 1, "Ingles");
        Alumno alu3 = new Alumno(3L, "nico.carrascol@duocuc.cl", 3, "Base De Datos Aplicada 1");

        list.add(alu1);
        list.add(alu2);
        list.add(alu3);
    }

    //Test para el metodo findById
    @Test
    @DisplayName("Validar que se obtenga correctamente un alumno por su ID")
    public void findByIdTest(){
        Alumno alumno = new Alumno(4L, "correo.prueba@gmail.com", 4, "Doctrina Social De La Iglesia");
        //Simulamos el comportamiento del repositorio
        when(alumnoRepository.findById(4L)).thenReturn(Optional.of(alumno));
        // Ejecutamos el método del servicio
        Optional<Alumno> result = alumnoServiceImpl.findById(4L);
        // Verificamos los datos
        assertTrue(result.isPresent());
        assertEquals("correo.prueba@gmail.com", result.get().getCorreo());
        assertEquals(4, result.get().getNivel());
        assertEquals("Doctrina Social De La Iglesia", result.get().getCurso());
        // Verificamos que se haya llamado al repositorio
        verify(alumnoRepository, times(1)).findById(4L);
    }

    //Test para el metodo findBySave
    @Test
    @DisplayName("Validar que guarde correctamente un alumno")
    public void saveAlumnoTest() {
        // Alumno a guardar
        Alumno alumnoToSave = new Alumno(10L, "nuevo@correo.com", 12, "Desarrollo FullStack 2");
        // Alumno simulado como si ya se hubiera guardado en la base de datos
        Alumno alumnoSaved = new Alumno(10L, "nuevo@correo.com", 12, "Desarrollo FullStack 2");
        // Simulamos el comportamiento del repositorio
        when(alumnoRepository.save(alumnoToSave)).thenReturn(alumnoSaved);
        // Ejecutamos el método real del servicio
        Alumno result = alumnoServiceImpl.save(alumnoToSave);
        // Validamos que no sea nulo y que tenga los valores esperados
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("nuevo@correo.com", result.getCorreo());
        assertEquals(12, result.getNivel());
        assertEquals("Desarrollo FullStack 2", result.getCurso());
        // Verificamos que se haya llamado al método save() del repositorio
        verify(alumnoRepository, times(1)).save(alumnoToSave);
    }

    //Test para el metodo delete
    @Test
    @DisplayName("Validar que elimine correctamente un alumno por su ID")
    public void deleteAlumnoTest() {
        Alumno alumno1 = new Alumno(15L, "alumno@correo.com", 13, "Base De Datos Aplicada 2");
        // Simulamos que se encuentra el alumno
        when(alumnoRepository.findById(15L)).thenReturn(Optional.of(alumno1));
        // Simulamos que no hace nada al eliminar
        doNothing().when(alumnoRepository).delete(alumno1);
        // Ejecutamos el método del servicio
        Optional<Alumno> result = alumnoServiceImpl.delete(alumno1);
        // Verificamos el resultado
        assertTrue(result.isPresent());
        assertEquals(15L, result.get().getId());
        // Verificamos que se llamaron los métodos correspondientes
        verify(alumnoRepository, times(1)).findById(15L);
        verify(alumnoRepository, times(1)).delete(alumno1);
    }
}
