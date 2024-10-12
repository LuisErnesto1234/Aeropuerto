package com.spring.aeropuerto.tarea.service;

import com.spring.aeropuerto.tarea.entity.Destino;
import com.spring.aeropuerto.tarea.repository.DestinoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinoService {

    private final DestinoRepository destinoRepository;

    public DestinoService(DestinoRepository destinoRepository) {
        this.destinoRepository = destinoRepository;
    }

    public List<Destino> listarDestinos(){
        return destinoRepository.findAll();
    }

    public Destino guardarDestino(Destino destino){
        return destinoRepository.save(destino);
    }

    public Destino obtenerDestinoPorId(Long id){
        return destinoRepository.findById(id).orElse(null);
    }

    public void eliminarDestino(Long id){
        destinoRepository.deleteById(id);
    }
}
