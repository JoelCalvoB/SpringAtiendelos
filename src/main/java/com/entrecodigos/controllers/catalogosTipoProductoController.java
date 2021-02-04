/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.TipoProductoDaoImp;
import com.entrecodigos.repositorios.TipoProducto;
import com.entrecodigos.repositorios.sucursales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author i7
 */
@RestController
@RequestMapping("/catalogos")
public class catalogosTipoProductoController {
     @Autowired
    @Qualifier("TipoProductoDaoImp")
     
       private TipoProductoDaoImp obj;

    @GetMapping("/tipoproductos")
    public Object getTipoProducto() {
        return obj.getTipoProducto();
    }
    @DeleteMapping("/tipoproductos/{identificador}")
    public Object eliminarTipoProducto(@PathVariable("identificador") String id) {
        return obj.eliminarTipoProducto(id);
    }
    
    @PostMapping("/tipoproductos")
    public Object insertarTipoProducto(@RequestBody TipoProducto p) {
        return obj.insertarTipoProducto(p);

    }
    @PutMapping("/tipoproductos")
    public Object ActualizaTipoProducto(@RequestBody TipoProducto p) {
        return obj.actualizaTipoProducto(p);
}
}
