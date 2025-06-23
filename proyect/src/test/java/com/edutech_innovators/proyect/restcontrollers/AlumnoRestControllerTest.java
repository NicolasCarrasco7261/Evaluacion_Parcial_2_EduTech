package com.edutech_innovators.proyect.restcontrollers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.edutech_innovators.proyect.entities.Alumno;
import com.edutech_innovators.proyect.services.AlumnoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
//imports de MockMvc
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class AlumnoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlumnoServiceImpl service;

    @Autowired 
    private ObjectMapper objectMapper;

    private List<Alumno> alumnosLista;

    // Testing para verTodosLosAlumnos
    @Test
    @DisplayName("Debería retornar código 200 y la lista de alumnos")
    public void verAlumnosTest() throws Exception {
        when(service.findByAll()).thenReturn(alumnosLista);
        
        mockMvc.perform(get("/api/alumnos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Testing verUnAlumno
    @Test
    @DisplayName("Debería retornar código 200 y los datos del alumno existente")
    public void verUnAlumnoTest() {
        Alumno unAlumno = new Alumno(1L, "alumno@correo.com", 3, "Inglés");
        try {
            when(service.findById(1L)).thenReturn(Optional.of(unAlumno));

            mockMvc.perform(get("/api/alumnos/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzó un error: " + ex.getMessage());
        }
    }

    // Testing alumnoNoExiste
    @Test
    @DisplayName("Debería retornar 404 si el alumno no existe")
    public void alumnoNoExisteTest() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/alumnos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // Testing crearAlumno
    @Test
    @DisplayName("Debería crear un alumno correctamente y retornar código 201")
    public void crearAlumnoTest() throws Exception {
        Alumno unAlumno = new Alumno(1L, "alumno@correo.com", 2, "Matemáticas");
        Alumno otroAlumno = new Alumno(2L, "nuevo@correo.com", 1, "Inglés");
        when(service.save(any(Alumno.class))).thenReturn(otroAlumno);

        mockMvc.perform(post("/api/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unAlumno)))
                .andExpect(status().isCreated());
    }

    // Testing modificarAlumno
    @Test
    @DisplayName("Debería modificar un alumno existente y retornar código 200")
    public void modificarAlumnoTest() throws Exception {
        Alumno existente = new Alumno(1L, "ca@mail.com", 2, "Inglés");
        Alumno modificado = new Alumno(1L, "ca@mail.com", 5, "Matemáticas");
        // Simula que se encuentra el alumno existente
        when(service.findById(1L)).thenReturn(Optional.of(existente));
        // Simula que se guarda correctamente el alumno modificado
        when(service.save(any(Alumno.class))).thenReturn(modificado);

        mockMvc.perform(put("/api/alumnos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk());
    }
    
    // Testing modificarAlumnoNoExiste
    @Test
    @DisplayName("Debería retornar 404 al intentar modificar un alumno inexistente")
    public void modificarAlumnoNoExisteTest() throws Exception {
        Alumno modificado = new Alumno(1L, "ca@mail.com", 5, "Python");
        // Simula que el alumno no existe en la base de datos
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/alumnos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isNotFound());
    }

    // Testing eliminarAlumnoExistente
    @Test
    @DisplayName("Debería eliminar un alumno existente y retornar código 200")
    public void eliminarAlumnoExistenteTest() throws Exception {
        Alumno alumno = new Alumno(1L, "mi@mail.com", 5, "Java");
        // Simula que el alumno fue encontrado y eliminado
        when(service.delete(any(Alumno.class))).thenReturn(Optional.of(alumno));

        mockMvc.perform(delete("/api/alumnos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Testing eliminarAlumnoNoExiste
    @Test
    @DisplayName("Debería retornar 404 al intentar eliminar un alumno que no existe")
    public void eliminarAlumnoNoExisteTest() throws Exception {
        // Simula que no se encuentra el alumno a eliminar
        when(service.delete(any(Alumno.class))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/alumnos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

