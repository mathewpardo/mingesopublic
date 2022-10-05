package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.entities.EmpleadoEntity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;
    public ArrayList<EmpleadoEntity> obtenerEmpleados(){return (ArrayList<EmpleadoEntity>) empleadoRepository.findAll();}



}
