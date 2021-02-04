/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.empresasDaoImp;
import com.entrecodigos.repositorios.empresas;
import com.entrecodigos.repositorios.usuarios_administrativos;
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
@RequestMapping("/administracion")
public class catalogosEmpresasController {

    @Autowired
    @Qualifier("objEmpresas")
    private empresasDaoImp obj;

    @GetMapping("/empresas")
    public Object gets() {
        return obj.gets();
    }

    @PostMapping("/empresas")
    public Object insertar(@RequestBody empresas e) {
        return obj.insertar(e);
    }

    @PutMapping("/empresas")
    public Object modificar(@RequestBody empresas e) {
        return obj.modificar(e);
    }
    
    @DeleteMapping("/empresas/{id}")
    public Object eliminar(@PathVariable("id") int id_sucursal){
        return obj.eliminar(id_sucursal);
    }
    
    
    @PostMapping("/login")
    public Object loginAdministrativo(@RequestBody usuarios_administrativos usuario){
       return obj.login(usuario);
    }
    
    @PostMapping("/login/empresa")
    public Object loginEmpresa(@RequestBody empresas usuario){
       return obj.loginEmpresa(usuario);
    }
    
    
    @GetMapping("/version")
    public Object version(){
      return obj.version();
    }
}
