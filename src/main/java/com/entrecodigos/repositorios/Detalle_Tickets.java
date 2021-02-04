/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;





public class Detalle_Tickets {
    
    
    private int id_ticket;
    private int id_producto;
    private double cantidad;
    private boolean servido;
    private int id;
    private String observaciones;
    private int tipo_producto;
    private boolean cancelado;
    private int cortesia;
    private double efectivo_porcentaje;
    private int id_empresa;
    private int id_sucursal;
    private boolean copia;

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public int getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(int tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isServido() {
        return servido;
    }

    public void setServido(boolean servido) {
        this.servido = servido;
    }  

    public int getCortesia() {
        return cortesia;
    }

    public void setCortesia(int cortesia) {
        this.cortesia = cortesia;
    }

    public double getEfectivo_porcentaje() {
        return efectivo_porcentaje;
    }

    public void setEfectivo_porcentaje(double efectivo_porcentaje) {
        this.efectivo_porcentaje = efectivo_porcentaje;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public boolean isCopia() {
        return copia;
    }

    public void setCopia(boolean copia) {
        this.copia = copia;
    }
    
    
}
