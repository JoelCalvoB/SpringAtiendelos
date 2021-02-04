/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.mesasDaoImp;
import com.entrecodigos.repositorios.mesas;
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
 * @author samv
 */
@RestController
@RequestMapping("/catalogos")
public class catalogosMesasController {

    @Autowired
    @Qualifier("objMesasDao")
    private mesasDaoImp obj;

    @GetMapping("/{empresa}/mesas/{identificador}")
    public Object gets(@PathVariable("identificador") String id,@PathVariable("empresa") String nombreEmpresa) {
      return   obj.gets(id,nombreEmpresa);
    }
    @GetMapping("/{empresa}/mesas/{identificador}/activos/{activo}")
    public Object gets(@PathVariable("identificador") String id,@PathVariable("activo") boolean activo,@PathVariable("empresa") String nombreEmpresa) {
      return   obj.gets(id,activo,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/mesas/{identificador}/activos/resumido/{offset}")
    public Object getsResumidos(@PathVariable("identificador") String id,@PathVariable("offset")String off,@PathVariable("empresa") String nombreEmpresa) {
      return   obj.getsResumidos(id,off,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/mesas/{identificador}/activos/{activo}/usuario/{id_usuario}")
    public Object gets(@PathVariable("identificador") String id,@PathVariable("activo") boolean activo,@PathVariable("id_usuario") int id_usuario,@PathVariable("empresa") String nombreEmpresa) {
      return   obj.gets(id,activo,id_usuario,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/sucursal/{id_sucursal}/mesa/{id_mesa}")
    public Object getEspecifico(@PathVariable("id_sucursal")String id_sucursal,@PathVariable("id_mesa")String id_mesa,@PathVariable("empresa") String nombreEmpresa) {
      return   obj.getEspecifico(id_sucursal,id_mesa,nombreEmpresa);
    }

    @PostMapping("/{empresa}/mesas")
    public Object insertar(@RequestBody mesas p,@PathVariable("empresa") String nombreEmpresa) {
        return obj.insertar(p,nombreEmpresa);
    }

    @PutMapping("/{empresa}/mesas")
    public Object actualizar(@RequestBody mesas p,@PathVariable("empresa") String nombreEmpresa) {
        return obj.actualizar(p,nombreEmpresa);
    }

    
    @DeleteMapping("/{empresa}/mesas/{id_sucursal}/{id_mesa}")
    public Object eliminar(@PathVariable("id_sucursal")String id_sucursal,@PathVariable("id_mesa")String id_mesa,@PathVariable("empresa") String nombreEmpresa) {
        return obj.eliminar(id_sucursal,id_mesa,nombreEmpresa);
    }
}
