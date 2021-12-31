package com.example.biblioteca.repositorys;

import com.example.biblioteca.collects.Recurso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecursoRepository extends MongoRepository<Recurso, String> {
    List<Recurso> findByTipo(String tipo);
    List<Recurso> findByTematica(String tematica);
}
