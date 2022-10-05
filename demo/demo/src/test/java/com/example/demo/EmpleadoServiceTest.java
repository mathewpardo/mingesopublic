package com.example.demo;

import com.example.demo.entities.EmpleadoEntity;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.services.EmpleadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTest {

    @Mock
    EmpleadoRepository empleadoRepository;

    @InjectMocks
    EmpleadoService empleadoService;

    @Test
    void obtenerEmpleadosTest(){
        EmpleadoEntity empleado1=new EmpleadoEntity("20.671.147-7","Mathew","Pardo", Date.valueOf("2001-01-29"), Date.valueOf("2020-01-29"),"A");
        EmpleadoEntity empleado2=new EmpleadoEntity("20.927.910-3","Marcelo","Sotomayor", Date.valueOf("2001-02-23"), Date.valueOf("2020-01-29"),"B");
        EmpleadoEntity empleado3=new EmpleadoEntity("19.231.442-3","Pedro","Jimenez", Date.valueOf("1996-06-15"), Date.valueOf("2020-01-29"),"C");
        ArrayList<EmpleadoEntity> empleados=new ArrayList<>();
        empleados.add(empleado1);
        empleados.add(empleado2);
        empleados.add(empleado3);
        when(empleadoRepository.findAll()).thenReturn(empleados);
        ArrayList<EmpleadoEntity> empleadosFinal=empleadoService.obtenerEmpleados();
        Assertions.assertEquals(empleados,empleadosFinal);
    }
}
