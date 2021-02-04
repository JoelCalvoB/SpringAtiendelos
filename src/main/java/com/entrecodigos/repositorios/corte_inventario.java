/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.Date;

/**
 *
 * @author samv
 */
public class corte_inventario {

    private int id_corte;
    private int id_inventario;
    private double cantidad_real;
    private double cantidad_programa;
    private Date fecha;
    private String hora;
    private int id_usuario;
    private int id_ubicacion;
    private int id_sucursal;
    private int id_empresa;

    public int getId_corte() {
        return id_corte;
    }

    public void setId_corte(int id_corte) {
        this.id_corte = id_corte;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public double getCantidad_real() {
        return cantidad_real;
    }

    public void setCantidad_real(double cantidad_real) {
        this.cantidad_real = cantidad_real;
    }

    public double getCantidad_programa() {
        return cantidad_programa;
    }

    public void setCantidad_programa(double cantidad_programa) {
        this.cantidad_programa = cantidad_programa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

   
    
    
}
