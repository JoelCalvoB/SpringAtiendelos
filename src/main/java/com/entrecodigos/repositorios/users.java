/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.repositorios;

import java.util.List;

/**
 *
 * @author samv
 */
public class users {

    private String login;
    private String nombre;
    private int idRol;
    private int idSucursal;
    private String password;
    private int idUser;
    private String nombreRol;
    private boolean menu;
    private boolean catalogos;
    private boolean bar;
    private boolean cocina;
    private boolean cuentas;
    private boolean transacciones;
    private int iniciarEn;
    private List<submenu> submenu;
    private boolean capitan;

    public boolean isMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu;
    }

    public boolean isCatalogos() {
        return catalogos;
    }

    public void setCatalogos(boolean catalogos) {
        this.catalogos = catalogos;
    }

    public boolean isBar() {
        return bar;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }

    public boolean isCocina() {
        return cocina;
    }

    public void setCocina(boolean cocina) {
        this.cocina = cocina;
    }

    public boolean isCuentas() {
        return cuentas;
    }

    public void setCuentas(boolean cuentas) {
        this.cuentas = cuentas;
    }

    public boolean isTransacciones() {
        return transacciones;
    }

    public void setTransacciones(boolean transacciones) {
        this.transacciones = transacciones;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public int getIniciarEn() {
        return iniciarEn;
    }

    public void setIniciarEn(int iniciarEn) {
        this.iniciarEn = iniciarEn;
    }

  

    public List<submenu> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<submenu> submenu) {
        this.submenu = submenu;
    }

    public boolean isCapitan() {
        return capitan;
    }

    public void setCapitan(boolean capitan) {
        this.capitan = capitan;
    }
    
    
}
