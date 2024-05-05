package com.example.trabajadoresfx;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;

public class Trabajador {
    private String nombre;
    private String puesto;
    private int salario;

    private Date fecha;


    public Trabajador(String nombre, String puesto, int salario) {
        this.nombre=nombre;
        this.puesto=puesto;
        this.salario=salario;

    }
    public int getSalario() {
        return salario;
    }
    public void setSalario(int salario) {
        this.salario = salario;
    }
    public String getPuesto() {
        return puesto;
    }
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    public String getNombre() {return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
