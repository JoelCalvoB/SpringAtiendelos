/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.rolesDaoImp;
import com.entrecodigos.repositorios.roles;
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
public class catalogosRolesController {
    @Autowired
    @Qualifier("rolesDao")
    private rolesDaoImp obj;
    
    @GetMapping("/{empresa}/roles")
    public Object gets(@PathVariable("empresa") String nombreEmpresa){
      return obj.gets(nombreEmpresa);
    }
    
    @PostMapping("/{empresa}/roles")
    public Object insertar(@RequestBody roles p,@PathVariable("empresa") String nombreEmpresa){    
        return obj.insertar(p,nombreEmpresa);
    }
    
    
    @PutMapping("/{empresa}/roles")
    public Object actualizar(@RequestBody roles p,@PathVariable("empresa") String nombreEmpresa){
      return obj.actualizar(p,nombreEmpresa);
    }
    
    @DeleteMapping("/{empresa}/roles/{identificador}")
    public Object eliminar(@PathVariable("identificador") int id_rol,@PathVariable("empresa") String nombreEmpresa){
        return obj.eliminar(id_rol,nombreEmpresa);
    }
}
