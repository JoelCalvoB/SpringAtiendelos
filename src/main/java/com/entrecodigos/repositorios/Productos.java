/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.List;

/**
 *
 * @author i7
 */

public class Productos {
    
    private int id_producto;
    private String nombre;
    private String descripcion;
    private int id_categoria;
    private double precio;
    private String id_video;
    private String nombre_video;
    private List<insumos> insumos;
    private String ruta_imagen;
    private String imagen;
    private boolean subirImagen;
    private int notificacion;
    private int cantidad;
    private double total;
    private String subcategoria;
    private List<menuDesplegable> menu_desplegable;
    private String tipo_menuDesplegable;

    public List<insumos> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<insumos> insumos) {
        this.insumos = insumos;
    }

    public String getNombre_video() {
        return nombre_video;
    }

    public void setNombre_video(String nombre_video) {
        this.nombre_video = nombre_video;
    }

    public String getId_video() {
        return id_video;
    }

    public void setId_video(String id_video) {
        this.id_video = id_video;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
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

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isSubirImagen() {
        return subirImagen;
    }

    public void setSubirImagen(boolean subirImagen) {
        this.subirImagen = subirImagen;
    }

    public int getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(int notificacion) {
        this.notificacion = notificacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public List<menuDesplegable> getMenu_desplegable() {
        return menu_desplegable;
    }

    public void setMenu_desplegable(List<menuDesplegable> menu_desplegable) {
        this.menu_desplegable = menu_desplegable;
    }

    public String getTipo_menuDesplegable() {
        return tipo_menuDesplegable;
    }

    public void setTipo_menuDesplegable(String tipo_menuDesplegable) {
        this.tipo_menuDesplegable = tipo_menuDesplegable;
    }
    
    
    
}
