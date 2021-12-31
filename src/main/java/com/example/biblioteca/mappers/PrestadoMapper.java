package com.example.biblioteca.mappers;

import com.example.biblioteca.DTOs.PrestadoDTO;
import com.example.biblioteca.collects.Prestado;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PrestadoMapper {

    public Prestado fromDTO(PrestadoDTO prestadoDTO){
        Prestado prestado = new Prestado();
        prestado.setId(prestadoDTO.getId());
        prestado.setIdRecurso(prestadoDTO.getIdRecurso());
        prestado.setFechaEntrega(prestadoDTO.getFechaEntrega());
        return prestado;
    }

    public PrestadoDTO fromCollection(Prestado prestado){
        PrestadoDTO prestadoDTO = new PrestadoDTO();
        prestadoDTO.setId(prestado.getId());
        prestadoDTO.setIdRecurso(prestado.getIdRecurso());
        prestadoDTO.setFechaEntrega(prestado.getFechaEntrega());
        return prestadoDTO;
    }

    public List<PrestadoDTO> fromCollectionList(List<Prestado> prestados){
        if(prestados == null){
            return null;
        }
        List<PrestadoDTO> list = new ArrayList<>(prestados.size());
        Iterator iterator = prestados.iterator();

        while (iterator.hasNext()){
            Prestado prestado = (Prestado) iterator.next();
            list.add(fromCollection(prestado));
        }
        return list;
    }
}
