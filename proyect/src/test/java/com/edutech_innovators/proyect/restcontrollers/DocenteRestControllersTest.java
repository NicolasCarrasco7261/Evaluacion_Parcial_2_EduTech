package com.edutech_innovators.proyect.restcontrollers;

import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; 
import org.springframework.test.web.servlet.MockMvc;
import com.edutech_innovators.proyect.entities.Docente;
import com.edutech_innovators.proyect.services.DocenteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Optional;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class DocenteRestControllersTest {


    @Autowired
    private MockMvc mockmvc;

    @MockitoBean
    private DocenteServiceImpl service;

    @Autowired 
    private ObjectMapper objectMapper; 

    private List<Docente> docentesLista;

    
    // Testing verTodosLosDocentes
    @Test
    public void verDocentesTest() throws Exception {

        when(service.findByAll()).thenReturn(docentesLista);
        mockmvc.perform(get("/api/docentes")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


    // Testing verUnDocente
    @Test 
    public void verunDocenteTest() {

        Docente unDocente = new Docente(1, "mi@outlook.cl", 10, "Python");
        try {

            when(service.findById(1L)).thenReturn(Optional.of(unDocente));
            mockmvc.perform(get("/api/docentes/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        }
        catch(Exception ex){
            fail("El testing lanzó un error" + ex.getMessage());
        }

    }


    // Testing docenteNoExiste
    @Test
    public void DocenteNoExisteTest() throws Exception{
        when(service.findById(1L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/docentes/1")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }


    // Testing crearDocente
    @Test
    public void crearDocenteTest () throws Exception {

        Docente unDocente = new Docente (1, "mi@outlook.cl", 10, "Python");
        Docente otroDocente = new Docente (2, "ca@outlook.cl", 5, "Java");
        when(service.save(any(Docente.class))).thenReturn(otroDocente);
        mockmvc.perform(post("/api/docentes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(unDocente)))
        .andExpect(status().isCreated());

    }

    // Testing modificarDocente
    @Test
    public void modificarDocenteTest() throws Exception {

        Docente existente = new Docente(1, "ca@mail.com", 5, "C++");
        Docente modificado = new Docente(1, "ca@mail.com", 10, "Java");

        // Simula que se encuentra el docente existente
        when(service.findById(1L)).thenReturn(Optional.of(existente));
        // Simula que se guarda correctamente
        when(service.save(any(Docente.class))).thenReturn(modificado);

        mockmvc.perform(put("/api/docentes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
            .andExpect(status().isOk());
    }



    // Testing modificarDocenteNoExiste
    @Test
    public void modificarDocenteNoExisteTest() throws Exception {

        Docente modificado = new Docente(1, "ca@mail.com", 5, "Python");
        // Simula que se docente no existe
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/docentes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modificado)))
            .andExpect(status().isNotFound());
    }



    // Testing eliminarDocenteExistente
    @Test
    public void eliminarDocenteExistenteTest() throws Exception {
        
        Docente docente = new Docente(1, "mi@mail.com", 5, "Java");
        // simula que elimina docente
        when(service.delete(any(Docente.class))).thenReturn(Optional.of(docente));

        mockmvc.perform(delete("/api/docentes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }



    // Testing eliminarDocenteNoExiste
    @Test
    public void eliminarDocenteNoExisteTest() throws Exception {

        when(service.delete(any(Docente.class))).thenReturn(Optional.empty());

        mockmvc.perform(delete("/api/docentes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }


}
