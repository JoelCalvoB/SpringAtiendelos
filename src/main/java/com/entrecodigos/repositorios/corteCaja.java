/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author samv
 */
public class corteCaja {
    private int id;
    private Date fecha;
    private Time hora;
    private double total;
    private int id_sucursal;
    private double efectivo_apertura;
    private boolean cerrada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public double getEfectivo_apertura() {
        return efectivo_apertura;
    }

    public void setEfectivo_apertura(double efectivo_apertura) {
        this.efectivo_apertura = efectivo_apertura;
    }

    public boolean isCerrada() {
        return cerrada;
    }

    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }
    
    
}
