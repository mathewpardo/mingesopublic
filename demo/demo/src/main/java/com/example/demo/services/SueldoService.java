package com.example.demo.services;


import com.example.demo.entities.*;
import com.example.demo.repositories.SueldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class SueldoService {

    @Autowired
    SueldoRepository sueldoRepository;

    @Autowired
    RelojService relojService;

    @Autowired
    SolicitudService solicitudService;

    @Autowired
    JustificativoService justificativoService;


    public ArrayList<SueldoEntity> obtenerSueldos(){return (ArrayList<SueldoEntity>) sueldoRepository.findAll();}

    public SueldoEntity guardarSueldo(SueldoEntity sueldo){return sueldoRepository.save(sueldo);}

    public int obtenerHorasExtra(ArrayList<RelojEntity> marcasEmpleado, ArrayList<SolicitudEntity> horasPermitidasEmpleado){
        int i,j;
        int horasExtra;
        int horasExtraTotales=0;
        LocalDate fecha;
        RelojEntity marcaSalida=null;
        LocalTime horarioSalida= LocalTime.of(18,0,0);
        Duration duracion;
        for (i=0;i<horasPermitidasEmpleado.size();i++){
            fecha=horasPermitidasEmpleado.get(i).getFechaHorasExtra();
            for (j=0;j<marcasEmpleado.size()-1;j++){
                if(marcasEmpleado.get(j).getFecha().isEqual(fecha)){
                    if(marcasEmpleado.get(j+1).getFecha().isEqual(fecha)){
                        marcaSalida=marcasEmpleado.get(j+1);
                        j=marcasEmpleado.size();
                    }
                }
            }
            if(marcaSalida!=null) {
                duracion = Duration.between(horarioSalida,marcaSalida.getHora());
                horasExtra = (int) (duracion.toHours());
                horasExtraTotales = horasExtraTotales + horasExtra;
            }
        }
        return horasExtraTotales;
    }

    public int sueldoHorasExtra(EmpleadoEntity empleado){
        int valorHora=0;
        if (empleado.getCategoria().equals("A")){
            valorHora=25000;
        } else if (empleado.getCategoria().equals("B")) {
            valorHora=20000;
        }else if (empleado.getCategoria().equals("C")){
            valorHora=10000;
        }
        return valorHora;
    }

    public int obtenerSueldoFijo(EmpleadoEntity empleado){
        int sueldo = 0;
        if (empleado.getCategoria().equals("A")){
            sueldo=1700000;
        } else if (empleado.getCategoria().equals("B")) {
            sueldo=1200000;
        }else if (empleado.getCategoria().equals("C")){
            sueldo=800000;
        }
        return sueldo;
    }




    public int obtenerAniosAntiguedad(EmpleadoEntity empleado){
        LocalDate fechaActual= LocalDate.now();
        LocalDate fecha= LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(empleado.getFecha_in()));
        //LocalDate fechaIngreso= empleado.getFecha_in().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period periodo= Period.between(fecha,fechaActual);
        return periodo.getYears();
    }

    public double calcularBonificaciones(EmpleadoEntity empleado){
        int anios=obtenerAniosAntiguedad(empleado);
        double bonificacion=0;
        if(anios>=25){
            bonificacion=obtenerSueldoFijo(empleado)*0.17;
        }else if(anios>=20){
            bonificacion=obtenerSueldoFijo(empleado)*0.14;
        }else if(anios>=15){
            bonificacion=obtenerSueldoFijo(empleado)*0.11;
        }else if(anios>=10){
            bonificacion=obtenerSueldoFijo(empleado)*0.08;
        }else if(anios>=5){
            bonificacion=obtenerSueldoFijo(empleado)*0.05;
        }
        return bonificacion;
    }

    public double bonoHorasExtra(EmpleadoEntity empleado){
        int horasExtra=obtenerHorasExtra(relojService.obtenerMarcasEmpleado(empleado.getRut()),solicitudService.obtenerPorRut(empleado.getRut()));
        return sueldoHorasExtra(empleado)*horasExtra;
    }

    public double calcularDescuentosAtraso(RelojEntity marca){
        LocalTime horaEntrada= LocalTime.of(8,0,0);
        long minutos=horaEntrada.until(marca.getHora(), ChronoUnit.MINUTES);
        double descuento=0;
        if (minutos>70){
            if(!justificativoService.verificarJustificativo(marca)) {
                descuento = 0.15;
            }
        }else if (minutos>45){
            descuento=0.06;
        }else if (minutos>25){
            descuento=0.03;
        }else if (minutos>10){
            descuento=0.01;
        }
        return descuento;
    }

    public ArrayList<RelojEntity> obtenerMarcasEntradaAtrasada(ArrayList<RelojEntity> marcasEmpleado){
        int j;
        LocalDate fecha;
        RelojEntity marcaEntrada;
        LocalTime horarioEntrada= LocalTime.of(8,0,0);
        ArrayList<RelojEntity> atrasos= new ArrayList<>();
        for (j=0;j< marcasEmpleado.size()-1;j++){
            fecha=marcasEmpleado.get(j).getFecha();
            if(marcasEmpleado.get(j+1).getFecha().isEqual(fecha)){
                marcaEntrada=marcasEmpleado.get(j);
                if (marcaEntrada.getHora().isAfter(horarioEntrada)) {
                    atrasos.add(marcaEntrada);
                }
            }
        }
        return atrasos;
    }

    public double calcularDescuentoAtrasosTotal(EmpleadoEntity empleado){
        int i;
        double descuentoTotal=0;
        ArrayList<RelojEntity> marcasConAtrasos=obtenerMarcasEntradaAtrasada(relojService.obtenerMarcasEmpleado(empleado.getRut()));
        for (i=0;i<marcasConAtrasos.size();i++){
            descuentoTotal=descuentoTotal+calcularDescuentosAtraso(marcasConAtrasos.get(i));
        }
        return descuentoTotal;
    }

    public double calcularDescuentoFaltas(EmpleadoEntity empleado){
        ArrayList<RelojEntity> marcasEmpleado=relojService.obtenerMarcasEmpleado(empleado.getRut());
        ArrayList<LocalDate> diasDeTrabajo = relojService.obtenerDiasTrabajables();
        int esta=0;
        int index=0;
        int diasFaltados=0;
        for (int i=0;i<diasDeTrabajo.size();i++){
            for(int j = 0; j<marcasEmpleado.size(); j++){
                if(marcasEmpleado.get(j).getFecha().isEqual(diasDeTrabajo.get(i))) {
                    esta=1;
                    j = marcasEmpleado.size();
                }
            }
            if(esta==0){
                if(!justificativoService.verificarJustificativo(diasDeTrabajo.get(i), empleado.getRut())){
                    diasFaltados++;
                }
            }
            esta=0;
        }
        return diasFaltados*0.15;
    }

    public double calcularDescuentosTotal(EmpleadoEntity empleado, int sueldoFijoEmpleado){
        double descuentos = (calcularDescuentoAtrasosTotal(empleado) + calcularDescuentoFaltas(empleado)) * sueldoFijoEmpleado;
        return descuentos;
    }

    public double calcularSueldoBruto(EmpleadoEntity empleado){
        int sueldoFijo= obtenerSueldoFijo(empleado);
        double bonificaciones=calcularBonificaciones(empleado);
        double bonoHoras=bonoHorasExtra(empleado);
        double descuentos=calcularDescuentosTotal(empleado,sueldoFijo);
        return sueldoFijo+bonificaciones+bonoHoras-descuentos;
    }


    public double cotizacionPrevisional(double sueldoBruto){
        return sueldoBruto*0.1;
    }

    public double cotizacionSalud(double sueldoBruto){
        return sueldoBruto*0.08;
    }

    public double calcularSueldoFinal(EmpleadoEntity empleado){
        double sueldoBruto=calcularSueldoBruto(empleado);
        return sueldoBruto - (cotizacionPrevisional(sueldoBruto)) - (cotizacionSalud(sueldoBruto));
    }

    public void calcularPlanillaEmpleado(EmpleadoEntity empleado){
        SueldoEntity nuevoSueldo;
        String rut;
        String nombres;
        String apellidos;
        String categoria;
        int sueldoFijo, sueldoBruto,sueldoFinal, bonoHorasExtra,bonoAnios,descuentos,cotPrevisional,cotSalud;
        int aniosDeAntiguedad;
        rut= empleado.getRut();
        nombres= empleado.getNombres();
        apellidos= empleado.getApellidos();
        categoria=empleado.getCategoria();
        aniosDeAntiguedad=obtenerAniosAntiguedad(empleado);
        sueldoFijo=obtenerSueldoFijo(empleado);
        bonoAnios=(int)calcularBonificaciones(empleado);
        bonoHorasExtra=(int)bonoHorasExtra(empleado);
        descuentos=(int)calcularDescuentosTotal(empleado,sueldoFijo);
        sueldoBruto=(int)calcularSueldoBruto(empleado);
        cotPrevisional=(int)cotizacionPrevisional(sueldoBruto);
        cotSalud=(int)cotizacionSalud(sueldoBruto);
        sueldoFinal=(int)calcularSueldoFinal(empleado);
        nuevoSueldo=sueldoRepository.save(new SueldoEntity(rut,apellidos,nombres,categoria,aniosDeAntiguedad,sueldoFijo,bonoAnios,bonoHorasExtra,descuentos,sueldoBruto,cotPrevisional,cotSalud,sueldoFinal));
        guardarSueldo(nuevoSueldo);
    }
}
