package com.example.demo.services;

import com.example.demo.entities.SolicitudEntity;
import com.example.demo.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SolicitudService {

    @Autowired
    SolicitudRepository solicitudRepository;

    public SolicitudEntity guardarSolicitud(SolicitudEntity solicitud){return solicitudRepository.save(solicitud);}

    public ArrayList<SolicitudEntity> obtenerPorRut(String rutEmpleado){return solicitudRepository.findAllByRut(rutEmpleado);}
}
