package com.example.trabajadoresfx;
import java.util.ArrayList;
import java.util.Collection;

public class listaTrabajadores {
    private ArrayList<Trabajador> lista;
    public listaTrabajadores() {lista = new ArrayList<>();}
    public ArrayList<Trabajador> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Trabajador> lista) {
        this.lista = lista;
    }

}

