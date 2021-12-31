package com.example.biblioteca.repositorys;

import com.example.biblioteca.collects.Prestado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestadoRepository extends MongoRepository<Prestado, String> {
    Prestado findByIdRecurso(String id);
}
