package com.example.biblioteca.controllers;

import com.example.biblioteca.DTOs.RecursoDTO;
import com.example.biblioteca.services.ServicioRecurso;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecursoControllerTest {

    @MockBean
    private ServicioRecurso servicioRecurso;

    @Autowired
    private MockMvc mockMvc;

    /*@Test
    @DisplayName("POST /recurso")
    void crearRecurso() throws Exception{

        var datoPost = new RecursoDTO();
        datoPost.setName("El privilegio de ser invisible");
        datoPost.setTipo("Pelicula");
        datoPost.setTematica("Drama");
        datoPost.setEstado(false);

        var datoReturn = new RecursoDTO();
        datoReturn.setId("1111");
        datoReturn.setName("El privilegio de ser invisible");
        datoReturn.setTipo("Pelicula");
        datoReturn.setTematica("Drama");
        datoReturn.setEstado(false);

        Mockito.when(servicioRecurso.saveRecurso(datoPost)).thenReturn(datoReturn);

        mockMvc.perform(post("/recurso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(datoPost)))

                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.LOCATION, "/recurso"))

                .andExpect(jsonPath("$.id", is("1111")))
                .andExpect(jsonPath("$.name", is("El privilegio de ser invisible")))
                .andExpect(jsonPath("$.tipo", is("Pelicula")))
                .andExpect(jsonPath("$.tematica", is("Drama")))
                .andExpect(jsonPath("$.estado", is(false)));
    }*/

    @Test
    @DisplayName("GET: /recursos")
    void obtenerTodosLosRecursos() throws Exception{
        var dato1 = new RecursoDTO();
        dato1.setId("5555");
        dato1.setName("El privilegio de ser invisible");
        dato1.setTipo("Pelicula");
        dato1.setTematica("Drama");
        dato1.setEstado(false);

        var dato2 = new RecursoDTO();
        dato2.setId("1111");
        dato2.setName("Fallen angel caído");
        dato2.setTipo("Libro");
        dato2.setTematica("Ciencia ficción");
        dato2.setEstado(false);

        var lista = new ArrayList<RecursoDTO>();
        lista.add(dato1);
        lista.add(dato2);

        Mockito.when(servicioRecurso.getAllRecursos()).thenReturn(lista);

        mockMvc.perform(get("/recursos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("5555")))
                .andExpect(jsonPath("$[1].id", is("1111")));
    }

    /*@Test
    @DisplayName("GET: /recurso/1 not-found")
    void obtenerUnRecursoNoFound() throws Exception{

        Mockito.when(servicioRecurso.getRecursoById(ArgumentMatchers.any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/recurso/{id}", "1"))
                .andExpect(status().isNotFound());
    }*/

    @Test
    @DisplayName("GET: /recurso/3333")
    void obtenerUnRecurso() throws Exception{
        var datoReturn = new RecursoDTO();
        datoReturn.setId("3333");
        datoReturn.setName("Pepito Juarez");
        datoReturn.setTipo("Caricatura");
        datoReturn.setTematica("Comedia");
        datoReturn.setEstado(false);

        Mockito.when(servicioRecurso.getRecursoById("3333")).thenReturn(Optional.of(datoReturn));

        mockMvc.perform(get("/recurso/{id}", "3333"))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.id", is("3333")))
                .andExpect(jsonPath("$.name", is("Pepito Juarez")))
                .andExpect(jsonPath("$.tipo", is("Caricatura")))
                .andExpect(jsonPath("$.tematica", is("Comedia")))
                .andExpect(jsonPath("$.estado", is(false)));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}