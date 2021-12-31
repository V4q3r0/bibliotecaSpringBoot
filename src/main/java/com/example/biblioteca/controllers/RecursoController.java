package com.example.biblioteca.controllers;

import com.example.biblioteca.DTOs.RecursoDTO;
import com.example.biblioteca.services.ServicioRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecursoController {

    @Autowired
    private ServicioRecurso servicioRecurso;

    @PostMapping(value = "/recurso")
    public ResponseEntity<RecursoDTO> guardarRecurso(@RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<>(servicioRecurso.saveRecurso(recursoDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/recurso/{id}")
    public ResponseEntity<RecursoDTO> obtenerRecurso(@PathVariable("id") String id){
        return new ResponseEntity<>(servicioRecurso.getRecursoById(id).get(), HttpStatus.OK);
    }

    @GetMapping(value = "/recursos")
    public ResponseEntity<List<RecursoDTO>> obtenerRecursos(){
        return new ResponseEntity<>(servicioRecurso.getAllRecursos(), HttpStatus.OK);
    }

    @PutMapping(value = "/recurso/actualizar")
    public ResponseEntity<RecursoDTO> actualizarRecurso(@RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<>(servicioRecurso.saveRecurso(recursoDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/recurso/eliminar/{id}")
    public ResponseEntity<String> deleteRecurso(@PathVariable("id") String id){
        return new ResponseEntity<>(servicioRecurso.deleteRecursoById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/consultarRecurso/{id}")
    public ResponseEntity<String> consultarRecurso(@PathVariable("id") String id){
        return new ResponseEntity<>(servicioRecurso.getRecurso(id), HttpStatus.OK);
    }

    @GetMapping(value = "/recursos/tipo/{tipo}")
    public ResponseEntity<List<RecursoDTO>> obtenerRecursosPorTipo(@PathVariable("tipo") String tipo){
        return new ResponseEntity<>(servicioRecurso.getRecursoByTipo(tipo), HttpStatus.OK);
    }

    @GetMapping(value = "/recursos/tematica/{tematica}")
    public ResponseEntity<List<RecursoDTO>> obtenerRecursosPorTematica(@PathVariable("tematica") String tematica){
        return new ResponseEntity<>(servicioRecurso.getRecursoByTematica(tematica), HttpStatus.OK);
    }

    @GetMapping(value = "/recurso/prestar/{id}")
    public ResponseEntity<String> prestarRecurso(@PathVariable("id") String id, @RequestBody String fecha){
        return new ResponseEntity<>(servicioRecurso.prestarRecurso(id, fecha), HttpStatus.OK);
    }

    @GetMapping(value = "/recurso/devolver/{id}")
    public ResponseEntity<String> devolverRecurso(@PathVariable("id") String id){
        return new ResponseEntity<>(servicioRecurso.devolverRecurso(id), HttpStatus.OK);
    }
}
