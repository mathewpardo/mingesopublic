package com.example.demo.controllers;

import com.example.demo.entities.JustificativoEntity;
import com.example.demo.services.JustificativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/justificativo")
public class JustificativoController {
    @Autowired
    private JustificativoService justificativoService;

    @PostMapping("/cargar")
    public ResponseEntity<JustificativoEntity> crearJustificativo(@RequestBody JustificativoEntity justificativo){
        JustificativoEntity nuevoJustificativo= justificativoService.guardarJustificativo(justificativo);
        return new ResponseEntity<>(nuevoJustificativo, HttpStatus.CREATED);
    }
}
