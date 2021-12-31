package com.example.biblioteca.services;

import com.example.biblioteca.DTOs.RecursoDTO;
import com.example.biblioteca.collects.Prestado;
import com.example.biblioteca.collects.Recurso;
import com.example.biblioteca.repositorys.PrestadoRepository;
import com.example.biblioteca.repositorys.RecursoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ServicioRecursoTest {

    @MockBean
    private RecursoRepository recursoRepository;
    @MockBean
    private PrestadoRepository prestadoRepository;

    @Autowired
    private ServicioRecurso servicioRecurso;

    @Test
    @DisplayName("Test para devolver un recurso")
    void devolverRecurso() {
        var recurso1 = new Recurso();
        recurso1.setId("3333");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(true);

        var prestado1 = new Prestado();
        prestado1.setId("1111");
        prestado1.setIdRecurso("3333");
        prestado1.setFechaEntrega("15/01/2022");

        Mockito.when(recursoRepository.findById(any())).thenReturn(Optional.of(recurso1));
        Mockito.when(prestadoRepository.findByIdRecurso(any())).thenReturn(prestado1);

        var resultado = servicioRecurso.devolverRecurso("3333");

        Assertions.assertEquals(false, recurso1.isEstado());
    }

    @Test
    @DisplayName("Test para obtener recursos por tematica")
    void obtenerRecursosPorTematica(){
        var recurso1 = new Recurso();
        recurso1.setId("0130");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Novela");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        var recurso2 = new Recurso();
        recurso2.setId("0240");
        recurso2.setName("Yerno millonario");
        recurso2.setTipo("Libro");
        recurso2.setTematica("Ciencia ficción");
        recurso2.setEstado(false);

        var lista = new ArrayList<Recurso>();
        lista.add(recurso1);
        lista.add(recurso2);

        Mockito.when(recursoRepository.findByTematica(any())).thenReturn(lista);

        var resultado = servicioRecurso.getRecursoByTematica("Ciencia ficción");

        Assertions.assertEquals("0130", resultado.get(0).getId());
        Assertions.assertEquals("Ciencia ficción", resultado.get(0).getTematica());
        Assertions.assertEquals("0240", resultado.get(1).getId());
        Assertions.assertEquals("Ciencia ficción", resultado.get(1).getTematica());
    }

    @Test
    @DisplayName("Test para obtener recursos por tipo")
    void obtenerRecursosPorTipo(){
        var recurso1 = new Recurso();
        recurso1.setId("0130");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        var recurso2 = new Recurso();
        recurso2.setId("0240");
        recurso2.setName("Yerno millonario");
        recurso2.setTipo("Libro");
        recurso2.setTematica("Drama");
        recurso2.setEstado(false);

        var lista = new ArrayList<Recurso>();
        lista.add(recurso1);
        lista.add(recurso2);

        Mockito.when(recursoRepository.findByTipo(any())).thenReturn(lista);

        var resultado = servicioRecurso.getRecursoByTipo("Libro");

        Assertions.assertEquals("0130", resultado.get(0).getId());
        Assertions.assertEquals("Libro", resultado.get(0).getTipo());
        Assertions.assertEquals("0240", resultado.get(1).getId());
        Assertions.assertEquals("Libro", resultado.get(1).getTipo());
    }

    @Test
    @DisplayName("Test para prestar un recurso de la biblioteca")
    void prestarRecurso(){
        var recurso1 = new Recurso();
        recurso1.setId("3333");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        Mockito.when(recursoRepository.findById(any())).thenReturn(Optional.of(recurso1));

        var mensaje = servicioRecurso.prestarRecurso("3333", "10/02/2022");
        var mensaje2 = servicioRecurso.prestarRecurso("3333", "13/05/2025");
        var resultado = servicioRecurso.getRecursoById("3333").get();

        Assertions.assertEquals("Haz prestado un recurso de la biblioteca", mensaje);
        Assertions.assertEquals(true, resultado.isEstado());
        Assertions.assertEquals("El recurso no se encuentra disponible.", mensaje2);
    }

    @Test
    @DisplayName("Test para obtener un recurso con estado true (prestado)")
    void obtenerRecursoPrestado(){
        var recurso1 = new Recurso();
        recurso1.setId("3333");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        var prestado1 = new Prestado();
        prestado1.setId("1111");
        prestado1.setIdRecurso("3333");
        prestado1.setFechaEntrega("15/01/2022");

        Mockito.when(recursoRepository.findById(any())).thenReturn(Optional.of(recurso1));
        Mockito.when(prestadoRepository.findByIdRecurso(any())).thenReturn(prestado1);

        var mensaje = servicioRecurso.getRecurso("3333");
        var mensaje2 = servicioRecurso.prestarRecurso("3333", "15/01/2022");
        var mensaje3 = servicioRecurso.getRecurso("3333");
        var resultado2 = servicioRecurso.getRecursoById("3333").get();

        Assertions.assertEquals("El recurso indicado está disponible.", mensaje);
        Assertions.assertEquals("El recurso indicado NO está disponible hasta: 15/01/2022", mensaje3);
    }

    @Test
    @DisplayName("Test para eliminar un recurso por id")
    void borrarRecursoPorId(){
        var recurso1 = new RecursoDTO();
        recurso1.setId("3333");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(true);

        var recurso2 = new Recurso();
        recurso2.setId("3333");
        recurso2.setName("Fallen angel caído");
        recurso2.setTipo("Libro");
        recurso2.setTematica("Ciencia ficción");
        recurso2.setEstado(true);

        Mockito.when(recursoRepository.findById(any())).thenReturn(Optional.of(recurso2));

        var resultado1 = servicioRecurso.saveRecurso(recurso1);
        var resultado = servicioRecurso.deleteRecursoById("3333");

        Assertions.assertEquals("Recurso eliminado", resultado);
    }

    @Test
    @DisplayName("Test obtener recurso por id")
    void obtenerRecursoPorId(){
        var recurso1 = new Recurso();
        recurso1.setId("5555");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        Mockito.when(recursoRepository.findById(any())).thenReturn(Optional.of(recurso1));

        var resultado = servicioRecurso.getRecursoById("5555").get();

        Assertions.assertEquals("5555", resultado.getId());
        Assertions.assertEquals("Fallen angel caído", resultado.getName());
        Assertions.assertEquals("Libro", resultado.getTipo());
        Assertions.assertEquals("Ciencia ficción", resultado.getTematica());
        Assertions.assertEquals(false, resultado.isEstado());
    }

    @Test
    @DisplayName("Test guardar un recurso")
    void guardarRecurso(){
        var recurso1 = new Recurso();
        recurso1.setId("1111");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        var recurso2 = new RecursoDTO();
        recurso2.setId("1111");
        recurso2.setName("Fallen angel caído");
        recurso2.setTipo("Libro");
        recurso2.setTematica("Ciencia ficción");
        recurso2.setEstado(false);

        Mockito.when(recursoRepository.save(any())).thenReturn(recurso1);

        var resultado = servicioRecurso.saveRecurso(recurso2);

        Assertions.assertEquals(recurso1.getId(), resultado.getId());
        Assertions.assertEquals(recurso1.getName(), resultado.getName());
        Assertions.assertEquals(recurso1.getTipo(), resultado.getTipo());
        Assertions.assertEquals(recurso1.getTematica(), resultado.getTematica());
        Assertions.assertEquals(recurso1.isEstado(), resultado.isEstado());
    }

    @Test
    @DisplayName("Test obtener todos los recursos")
    void obtenerTodosLosRecursos(){
        var recurso1 = new Recurso();
        recurso1.setId("0130");
        recurso1.setName("Fallen angel caído");
        recurso1.setTipo("Libro");
        recurso1.setTematica("Ciencia ficción");
        recurso1.setEstado(false);

        var recurso2 = new Recurso();
        recurso2.setId("0240");
        recurso2.setName("Yerno millonario");
        recurso2.setTipo("Novela");
        recurso2.setTematica("Drama");
        recurso2.setEstado(false);

        var lista = new ArrayList<Recurso>();
        lista.add(recurso1);
        lista.add(recurso2);

        Mockito.when(recursoRepository.findAll()).thenReturn(lista);

        var resultado = servicioRecurso.getAllRecursos();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals(recurso1.getId(), resultado.get(0).getId());
        Assertions.assertEquals(recurso1.getName(), resultado.get(0).getName());
        Assertions.assertEquals(recurso1.getTipo(), resultado.get(0).getTipo());
        Assertions.assertEquals(recurso1.getTematica(), resultado.get(0).getTematica());
        Assertions.assertEquals(recurso1.isEstado(), resultado.get(0).isEstado());

        Assertions.assertEquals(recurso2.getId(), resultado.get(1).getId());
        Assertions.assertEquals(recurso2.getName(), resultado.get(1).getName());
        Assertions.assertEquals(recurso2.getTipo(), resultado.get(1).getTipo());
        Assertions.assertEquals(recurso2.getTematica(), resultado.get(1).getTematica());
        Assertions.assertEquals(recurso2.isEstado(), resultado.get(1).isEstado());
    }


}