package com.example.demo.controllers;

import com.example.demo.services.EmpleadoService;
import com.example.demo.entities.EmpleadoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/empleado")
public class EmpleadoController {
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<EmpleadoEntity>empleados=empleadoService.obtenerEmpleados();
        model.addAttribute("empleados",empleados);
        return "index";
    }
}
