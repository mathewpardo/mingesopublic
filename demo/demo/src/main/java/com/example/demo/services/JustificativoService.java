package com.example.demo.services;


import com.example.demo.entities.JustificativoEntity;
import com.example.demo.entities.RelojEntity;
import com.example.demo.repositories.JustificativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class JustificativoService {
    @Autowired
    JustificativoRepository justificativoRepository;

    public JustificativoEntity guardarJustificativo(JustificativoEntity justificativo){return justificativoRepository.save(justificativo);}

    public ArrayList<JustificativoEntity> obtenerJustificativosEmpleado(String rut){return justificativoRepository.findAllByRut(rut);}

    public boolean verificarJustificativo(RelojEntity marca){
        ArrayList<JustificativoEntity> justificativosEmpleado=obtenerJustificativosEmpleado(marca.getRut());
        LocalDate fecha=marca.getFecha();
        int i;
        for (i=0;i<justificativosEmpleado.size();i++){
            if(fecha.isEqual(justificativosEmpleado.get(i).getFecha())){
                return true;
            }
        }
        return false;
    }

    public boolean verificarJustificativo(LocalDate fecha,String rutEmpleado){
        ArrayList<JustificativoEntity> justificativosEmpleado=obtenerJustificativosEmpleado(rutEmpleado);
        int i;
        for (i=0;i<justificativosEmpleado.size();i++){
            if(fecha.isEqual(justificativosEmpleado.get(i).getFecha())){
                return true;
            }
        }
        return false;
    }
}
