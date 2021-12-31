package com.example.biblioteca.services;

import com.example.biblioteca.DTOs.RecursoDTO;
import com.example.biblioteca.collects.Prestado;
import com.example.biblioteca.collects.Recurso;
import com.example.biblioteca.mappers.PrestadoMapper;
import com.example.biblioteca.mappers.RecursoMapper;
import com.example.biblioteca.repositorys.PrestadoRepository;
import com.example.biblioteca.repositorys.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioRecurso {

    @Autowired
    private RecursoRepository recursoRepository;
    @Autowired
    private PrestadoRepository prestadoRepository;

    RecursoMapper recursoMapper = new RecursoMapper();
    PrestadoMapper prestadoMapper = new PrestadoMapper();

    public Optional<RecursoDTO> getRecursoById(String id){
        Recurso recurso = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
        return Optional.of(recursoMapper.fromCollection(recurso));
    }

    public List<RecursoDTO> getAllRecursos(){
        return recursoMapper.fromCollectionList(recursoRepository.findAll());
    }

    public RecursoDTO saveRecurso(RecursoDTO recursoDTO){
        Recurso recurso = recursoMapper.fromDTO(recursoDTO);
        recursoRepository.save(recurso);
        return recursoDTO;
    }

    public String deleteRecursoById(String id){
        var recurso = recursoRepository.findById(id).get();
        try{
            recursoRepository.deleteById(recurso.getId());
            return "Recurso eliminado";
        }catch (Exception e){
            return "No se pudo eliminar el recurso";
        }
    }

    public String devolverRecurso(String id){
        var recurso = recursoRepository.findById(id).get();

        if(recurso.isEstado()){
            var prestado = prestadoRepository.findByIdRecurso(id);
            recurso.setEstado(false);
            recursoRepository.save(recurso);
            prestadoRepository.deleteById(prestado.getId());
            return "Haz devuelto el recurso.";
        }
        return "El recurso no se encuenta prestado.";
    }

    public String prestarRecurso(String id, String fecha){
        var recurso = recursoRepository.findById(id).get();

        if(!recurso.isEstado()){
            var prestado = new Prestado();
            prestado.setIdRecurso(id);
            prestado.setFechaEntrega(fecha);
            prestadoRepository.save(prestado);
            recurso.setEstado(true);
            recursoRepository.save(recurso);
            return "Haz prestado un recurso de la biblioteca";
        }
        return "El recurso no se encuentra disponible.";
    }

    public String getRecurso(String id){
        var recurso = recursoRepository.findById(id).get();
        if(recurso.isEstado()){
            var prestado = prestadoRepository.findByIdRecurso(id);
            return "El recurso indicado NO está disponible hasta: "+prestado.getFechaEntrega();
        }
        return "El recurso indicado está disponible.";
    }

    public List<RecursoDTO> getRecursoByTipo(String tipo){
        return recursoMapper.fromCollectionList(recursoRepository.findByTipo(tipo));
    }

    public List<RecursoDTO> getRecursoByTematica(String tematica){
        return recursoMapper.fromCollectionList(recursoRepository.findByTematica(tematica));
    }
}
