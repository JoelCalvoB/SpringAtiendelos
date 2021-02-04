/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author i7
 */
public class Promociones {
    
    private int id;
    private String nombre;
    private String descripcion;
    private List<String> dias;
    private String inicia;
    private String termina;
    private double precio;
    private String productos;
    private List<String> productoscodificado;
    private List<Productos> listaProductos;
    private int cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getInicia() {
        return inicia;
    }

    public void setInicia(String inicia) {
        this.inicia = inicia;
    }

    public String getTermina() {
        return termina;
    }

    public void setTermina(String termina) {
        this.termina = termina;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<String> getDias() {
        return dias;
    }

    public void setDias(List<String> dias) {
        this.dias = dias;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public List<String> getProductoscodificado() {
        return productoscodificado;
    }

    public void setProductoscodificado(List<String> productoscodificado) {
        this.productoscodificado = productoscodificado;
    }

    public List<Productos> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

 

    
    
}
