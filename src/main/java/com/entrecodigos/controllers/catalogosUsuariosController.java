/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.usuariosDaoImp;
import com.entrecodigos.repositorios.users;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class catalogosUsuariosController {

    @Autowired
    @Qualifier("objUsuariosDao")
    private usuariosDaoImp objUsuarios;

    @GetMapping("/{empresa}/usuarios")
    public Object getUsuarios(@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.getUsuarios(nombreEmpresa);
    }

    @GetMapping("/{empresa}/usuarios/{identificador}")
    public Object getUsuarioEspecifico(@PathVariable("identificador") String id,@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.getUsuarioEspecifico(id,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/usuarios/buscarusuario/{identificador}")
    public Object getUsuarioEspecificoUusario(@PathVariable("identificador") String usuario,@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.getUsuarioEspecifico(usuario,nombreEmpresa);
    }

    @DeleteMapping("/{empresa}/usuarios/{identificador}")
    public Object eliminarUsuario(@PathVariable("identificador") String id,@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.eliminar(id,nombreEmpresa);
    }

    @PostMapping("/{empresa}/usuarios")
    public Object insertarUsuarios(@RequestBody users p,@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.insertar(p,nombreEmpresa);
    }

    @PutMapping("/{empresa}/usuarios")
    public Object actualizaUsuarios(@RequestBody users algo,@PathVariable("empresa") String nombreEmpresa) {

        return objUsuarios.actualiza(algo,nombreEmpresa);
    }
    
    
    @PostMapping("/{empresa}/usuarios/login")
    public Object login(@RequestBody users p,@PathVariable("empresa") String nombreEmpresa){
       return objUsuarios.login(p,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/usuarios/lista/{id_sucursal}")
    public Object getUsuariosCambiar(@PathVariable("id_sucursal")String id_sucursal,@PathVariable("empresa") String nombreEmpresa){
      return objUsuarios.getUsuariosCambiar(id_sucursal,nombreEmpresa);
    }
    
    
    @GetMapping("/{empresa}/usuarios/reactivando")
    public Object reactivando(@PathVariable("empresa") String nombreEmpresa){
        Map<String,Object> respuesta = new HashMap<String,Object>();
        respuesta.put("respuesta","Servicio reactivado");
        return respuesta;
    }
    
    @GetMapping("/{empresa}/usuarios/status/{id_sucursal}")
    public Object getValidaAcceso(@PathVariable ("id_sucursal")String id_sucursal,@PathVariable("empresa") String nombreEmpresa) {
        return objUsuarios.getValidaAcceso(id_sucursal);
    }
}
