package com.example.demo.controllers;


import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.entities.SueldoEntity;
import com.example.demo.services.EmpleadoService;
import com.example.demo.services.SueldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value ="/planillas")
public class SueldoController {

    @Autowired
    SueldoService sueldoService;

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/ver")
    public String mostrarDatos(Model model){
        ArrayList<EmpleadoEntity> empleados=empleadoService.obtenerEmpleados();
        EmpleadoEntity empleadoActual;
        for(int i=0;i<empleados.size();i++){
            empleadoActual=empleados.get(i);
            sueldoService.calcularPlanillaEmpleado(empleadoActual);
        }
        ArrayList<SueldoEntity> sueldos= sueldoService.obtenerSueldos();
        model.addAttribute("sueldos",sueldos);
        return "planillasueldos";
    }


}
