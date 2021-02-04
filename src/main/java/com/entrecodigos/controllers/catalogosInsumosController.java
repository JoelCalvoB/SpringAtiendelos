/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.InsumosDaoImp;
import com.entrecodigos.repositorios.Productos;
import com.entrecodigos.repositorios.insumos;
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
public class catalogosInsumosController {
    
    @Autowired
    @Qualifier("objInsumos")
    private InsumosDaoImp obj;

    @GetMapping("/{empresa}/productos/{sucursal}/insumos/{identificador}")
    public Object getInsumo(@PathVariable("identificador") String id_producto,@PathVariable("sucursal")String id_sucursal,@PathVariable("empresa")String nombreEmpresa) {
        return obj.getInsumo(id_producto,id_sucursal);
    }

    @DeleteMapping("/{empresa}/insumo/{id_inventario}/{id_producto}")
    public Object DeleteInsumo(@PathVariable("id_inventario")Integer id_inventario, @PathVariable("id_producto") Integer id_producto,@PathVariable("empresa")String nombreEmpresa) {
        return obj.DeleteInsumo(id_inventario, id_producto);
    }
    
    @PostMapping("/{empresa}/productos/insumos")
    public Object insertarInsumo(@RequestBody Productos p,@PathVariable("empresa")String nombreEmpresa) {
        return obj.InsertaInsumo(p);

    }
    
   
    
    @GetMapping("/{empresa}/calculoinsumo/{cantidad}/{id_producto}")
    public Object CalculaInsumos(@PathVariable ("cantidad") Integer cantidad,@PathVariable("id_producto") Integer id_producto ,@PathVariable("empresa")String nombreEmpresa){
        return obj.CalculaInsumos(cantidad,id_producto);
    }
}
