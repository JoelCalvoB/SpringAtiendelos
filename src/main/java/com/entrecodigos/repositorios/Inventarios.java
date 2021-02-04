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
public class Inventarios {

    private String unidad_medida;
    private String descripcion;
    private String marca;
    private String ubicacion;
    private int id_sucursal;
    private int id_inventario;
    private String categoria;
    private double minimo;
    private int id_empresa;
    private String codigo;
    private int id_proveedor;
    private double costo_unitario;
    private String nota;
    private int cantidad;
    private int id_usuario;
    private String nombre_bodega;
    private String ubicacion_bodega;
    private String responsable_bodega;
    private String tiempo_real;
    private int id_bodega;
    private String nombre; 
    
    
    private int folio ;
    private Date fecha;
    private List<ordenescompra> ordenescompra;
    private String elaboro;

    public String getElaboro() {
        return elaboro;
    }

    public void setElaboro(String elaboro) {
        this.elaboro = elaboro;
    }

    
    
    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ordenescompra> getOrdenescompra() {
        return ordenescompra;
    }

    public void setOrdenescompra(List<ordenescompra> ordenescompra) {
        this.ordenescompra = ordenescompra;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public int getId_bodega() {
        return id_bodega;
    }

    public void setId_bodega(int id_bodega) {
        this.id_bodega = id_bodega;
    }

    public String getTiempo_real() {
        return tiempo_real;
    }

    public void setTiempo_real(String tiempo_real) {
        this.tiempo_real = tiempo_real;
    }

    public String getNombre_bodega() {
        return nombre_bodega;
    }

    public void setNombre_bodega(String nombre_bodega) {
        this.nombre_bodega = nombre_bodega;
    }

    public String getUbicacion_bodega() {
        return ubicacion_bodega;
    }

    public void setUbicacion_bodega(String ubicacion_bodega) {
        this.ubicacion_bodega = ubicacion_bodega;
    }

    public String getResponsable_bodega() {
        return responsable_bodega;
    }

    public void setResponsable_bodega(String responsable_bodega) {
        this.responsable_bodega = responsable_bodega;
    }

  

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getMinimo() {
        return minimo;
    }

    public void setMinimo(double minimo) {
        this.minimo = minimo;
    }

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public double getCosto_unitario() {
        return costo_unitario;
    }

    public void setCosto_unitario(double costo_unitario) {
        this.costo_unitario = costo_unitario;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    
}
