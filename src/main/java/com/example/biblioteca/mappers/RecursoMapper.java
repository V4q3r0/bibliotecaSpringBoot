package com.example.biblioteca.mappers;

import com.example.biblioteca.DTOs.RecursoDTO;
import com.example.biblioteca.collects.Recurso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursoMapper {

    public Recurso fromDTO(RecursoDTO recursoDTO){
        Recurso recurso = new Recurso();
        recurso.setId(recursoDTO.getId());
        recurso.setName(recursoDTO.getName());
        recurso.setTipo(recursoDTO.getTipo());
        recurso.setTematica(recursoDTO.getTematica());
        recurso.setEstado(recursoDTO.isEstado());
        return recurso;
    }

    public RecursoDTO fromCollection(Recurso recurso){
        RecursoDTO recursoDTO = new RecursoDTO();
        recursoDTO.setId(recurso.getId());
        recursoDTO.setName(recurso.getName());
        recursoDTO.setTipo(recurso.getTipo());
        recursoDTO.setTematica(recurso.getTematica());
        recursoDTO.setEstado(recurso.isEstado());
        return recursoDTO;
    }

    public List<RecursoDTO> fromCollectionList(List<Recurso> recursos){
        if(recursos == null){
            return null;
        }
        List<RecursoDTO> list = new ArrayList<>(recursos.size());
        Iterator iterator = recursos.iterator();

        while (iterator.hasNext()){
            Recurso recurso = (Recurso) iterator.next();
            list.add(fromCollection(recurso));
        }
        return list;
    }

}
