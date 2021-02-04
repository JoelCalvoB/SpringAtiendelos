/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.Date;
import java.util.List;

/**
 *
 * @author i7
 */
public class Tickets {
    
    private int idTicket;
    private int idUser;
    private Date fecha;
    private int idFolio;
    private int idSucursal;
    private int idMesa;
    private double total;
    private String tipoPago;
    private List<Promociones> promociones;
    private String nombre;
    private List<Integer> mesasAfectadas;
    private int id_sucursal;
    private String fechaInserta;
    public int cortesia;
    public double efectivo_porcentaje;
    private double iva;
    private double tarjeta;
    private String listac;
    private String tipoPropina;
    private double propina;
    private String nuevo;


    public String getNuevo() {
		return nuevo;
	}

	public void setNuevo(String nuevo) {
		this.nuevo = nuevo;
	}

	public String getListac() {
        return listac;
    }

    public void setListac(String listac) {
        this.listac = listac;
    }
    

    
    public String getFechaInserta() {
        return fechaInserta;
    }

    public void setFechaInserta(String fechaInserta) {
        this.fechaInserta = fechaInserta;
    }

    
    

 


    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }
    

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(int idFolio) {
        this.idFolio = idFolio;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public List<Promociones> getPromociones() {
        return promociones;
    }

    public void setPromociones(List<Promociones> promociones) {
        this.promociones = promociones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getMesasAfectadas() {
        return mesasAfectadas;
    }

    public void setMesasAfectadas(List<Integer> mesasAfectadas) {
        this.mesasAfectadas = mesasAfectadas;
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

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(double tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getTipoPropina() {
        return tipoPropina;
    }

    public void setTipoPropina(String tipoPropina) {
        this.tipoPropina = tipoPropina;
    }

    public double getPropina() {
        return propina;
    }

    public void setPropina(double propina) {
        this.propina = propina;
    }

    
  
}
