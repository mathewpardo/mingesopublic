package com.example.demo.controllers;

import com.example.demo.entities.SolicitudEntity;
import com.example.demo.services.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/solicitud")
public class SolicitudController {

    @Autowired
    SolicitudService solicitudService;

    @PostMapping("/cargar")
    public ResponseEntity<SolicitudEntity> crearSolicitud(@RequestBody SolicitudEntity solicitud){
        SolicitudEntity nuevaSolicitud = solicitudService.guardarSolicitud(solicitud);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }
}
