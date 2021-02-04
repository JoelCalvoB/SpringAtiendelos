/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.ProductosDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.entrecodigos.repositorios.Productos;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/catalogos")
public class catalogosProductosController {

    @Autowired
    @Qualifier("objProductos")
    private ProductosDaoImp obj;

    @GetMapping("/{empresa}/productos")
    public Object getProductos(@PathVariable("empresa") String empresa) {
        return obj.getProductos(empresa);
    }

    @DeleteMapping("/{empresa}/productos/{identificador}")
    public Object eliminarProducto(@PathVariable("identificador") String id,@PathVariable("empresa") String empresa) {
        return obj.elimnarProducto(id,empresa);
    }

    @PutMapping("/{empresa}/productos")
    public Object Actualiza(@RequestBody Productos p,@PathVariable("empresa") String empresa) {
        return obj.ActualizaProducto(p,empresa);

    }

    @PostMapping("/{empresa}/productos")
    public Object Inserta(@RequestBody Productos p,@PathVariable("empresa") String empresa) {
        return obj.InsertarProducto(p,empresa);
    }

    @GetMapping("/{empresa}/productos/categoria/{id}")
    public Object seleccionaProducto(@PathVariable("id") String id,@PathVariable("empresa") String empresa) {
        return obj.seleccionaProducto(id,empresa);
    }
    
    @GetMapping("/{empresa}/productos/categoria/{id}/subcategoria/{id_sub}")
    public Object seleccionaProducto(@PathVariable("id") String id,@PathVariable("id_sub") String id_categoria,@PathVariable("empresa") String empresa) {
        return obj.seleccionaProductoSubcategoria(id,id_categoria,empresa);
    }

    @GetMapping("/{empresa}/productos/lista/categorias")
    public Object seleccionarListaPorCategorias(@PathVariable("empresa") String empresa) {
        return obj.seleccionarListaPorCategorias(empresa);
    }
}
