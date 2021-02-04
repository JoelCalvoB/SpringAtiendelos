/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author i7
 */
public class Combos {
    private int id_combo;
    private String  nombre;
    private String descripcion;
    private double precio;
    private List<Detalle_combo> detalle;

    public List<Detalle_combo> getDetalle() {        
        return detalle;
    }

    public void setDetalle(List<Detalle_combo> detalle) {
        this.detalle = detalle;
    }

    public int getId_combo() {
        return id_combo;
    }

    public void setId_combo(int id_combo) {
        this.id_combo = id_combo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

 
}
