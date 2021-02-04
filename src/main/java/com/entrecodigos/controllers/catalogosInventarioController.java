/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.InventariosDaoImp;
import com.entrecodigos.funciones.empresaPropiedades;
import com.entrecodigos.repositorios.Inventarios;
import com.entrecodigos.repositorios.Productos;

import com.entrecodigos.repositorios.control_movimientos;
import com.entrecodigos.repositorios.corte_inventario;
import com.entrecodigos.repositorios.proveedores;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
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
 * @author sistemas
 */
@RestController
@RequestMapping("/catalogos")

public class catalogosInventarioController {

    @Autowired
    @Qualifier("objInventarios")

    private InventariosDaoImp obj;

    @GetMapping("/{empresa}/inventarios")
    public Object getInventario(@PathVariable("empresa") String empresa) {
        return obj.getInventario(empresa);
    }

    @GetMapping("/{empresa}/inventariosproveedores")
    public Object getProveedores(@PathVariable("empresa") String empresa) {
        return obj.getProveedores(empresa);
    }

    @GetMapping("/{empresa}/inventarios/medidas")
    public Object getMedidas(@PathVariable("empresa") String empresa) {
        return obj.getMedidas(empresa);
    }

    @GetMapping("/{empresa}/inventariosbodega/{id_sucursal}")
    public Object getBodega(@PathVariable("empresa") String empresa, @PathVariable("id_sucursal") int id_sucursal) {
        return obj.getBodega(empresa, id_sucursal);
    }

    @GetMapping("/{empresa}/inventariosbodegaespecifico/{id_bodega}")
    public Object getBodegaEspecifico(@PathVariable("empresa") String empresa, @PathVariable("id_bodega") int id_bodega) {
        return obj.getBodegaEspecifico(empresa, id_bodega);
    }

    @GetMapping("/{empresa}/inventarios/ordenes/{folio}")
    public Object getDesgloseFolioOrden(@PathVariable("empresa") String empresa, @PathVariable("folio") int folio) {
        return obj.getDesgloseFolioOrden(empresa, folio);
    }

    @GetMapping("/{empresa}/inventarios/lista/ordenes")
    public Object getOrdenesCompra(@PathVariable("empresa") String empresa) {
        return obj.getOrdenesCompra(empresa);
    }

    @GetMapping("/{empresa}/inventarios/historial/{id_inventario}/{id_sucursal}")
    public Object CorteProducto(@PathVariable("id_inventario") int id_inventario, @PathVariable("id_sucursal") int id_sucursal, @PathVariable("empresa") String empresa) {
        return obj.CorteProducto(id_inventario, id_sucursal, empresa);
    }

    @GetMapping("/empresa/inventarios/{descripcion}")
    public Object getInventarioEspecifico(@PathVariable("descripcion") String descripcion, @PathVariable("empresa") String empresa) {
        return obj.getInventarioespecifico(descripcion);
    }

    @GetMapping("/{empresa}/inventarios/{id_inventario}/sucursal/{id_sucursal}/desglose/{id_ubicacion}")
    public Object getHistorial(@PathVariable("id_inventario") String id_inventario, @PathVariable("empresa") String empresa, @PathVariable("id_sucursal") int id_sucursal,@PathVariable("id_ubicacion") int id_ubicacion) {
        return obj.getHistorial(id_inventario, empresa, id_sucursal,id_ubicacion);
    }

    @DeleteMapping("/{empresa}/inventarios/{identificador}")
    public Object DeleteInventario(@PathVariable("identificador") Integer identificador, @PathVariable("empresa") String empresa) {
        return obj.DeleteInventario(identificador);
    }

    @DeleteMapping("/{empresa}/inventarios/proveedores/{identificador}")
    public Object DeleteProveedor(@PathVariable("identificador") Integer identificador, @PathVariable("empresa") String empresa) {
        return obj.DeleteProveedor(identificador);
    }

    @DeleteMapping("/{empresa}/inventarios/bodega/{identificador}")
    public Object DeleteBodega(@PathVariable("identificador") Integer identificador, @PathVariable("empresa") String empresa) {
        return obj.DeleteBodega(identificador);
    }

    @DeleteMapping("/{empresa}/inventarios/medida/{identificador}")
    public Object DeleteMedida(@PathVariable("identificador") Integer identificador, @PathVariable("empresa") String empresa) {
        return obj.DeleteMedida(identificador);
    }

    @PutMapping("/{empresa}/inventarios")
    public Object ActualizaInventario(@RequestBody Inventarios p, @PathVariable("empresa") String empresa) {
        return obj.ActualizarInventario(p);
    }

    @PostMapping("/{empresa}/inventarios")
    public Object InsertaInventario(@RequestBody Inventarios p, @PathVariable("empresa") String empresa) {
        return obj.InsertaInventario(p, empresa);
    }

    @PostMapping("/{empresa}/inventarios/proveedor")
    public Object InsertarProveedor(@RequestBody proveedores p, @PathVariable("empresa") String empresa) {
        return obj.InsertarProveedor(p, empresa);
    }

    @PostMapping("/{empresa}/inventarios/bodega")
    public Object InsertarBodega(@RequestBody Inventarios p, @PathVariable("empresa") String empresa) {
        return obj.InsertarBodega(p, empresa);
    }

    @PostMapping("/{empresa}/inventarios/medida")
    public Object InsertarMedida(@RequestBody Inventarios p, @PathVariable("empresa") String empresa) {
        return obj.InsertarMedida(p, empresa);
    }

    @PostMapping("/{empresa}/inventarios/insertar/orden")
    public Object InsertaOrden(@RequestBody Inventarios a, @PathVariable("empresa") String empresa) {
        return obj.InsertaOrden(empresa, a);
    }

    @PutMapping("/{empresa}/inventariosajustes")
    public Object AjusteMovimiento(@RequestBody Inventarios p, @PathVariable("empresa") String empresa) {
        return null;
    }

    @PutMapping("/{empresa}/inventarios/ajustes/lista")
    public Object AjusteMovimientoLista(@RequestBody List<control_movimientos> p, @PathVariable("empresa") String empresa) {
        return obj.AjusteMovimiento(p, empresa);
    }

    @PutMapping("/{empresa}/inventarios/proveedor")
    public Object ActualizaProveedor(@RequestBody proveedores p) {
        return obj.ActualizaProveedor(p);
    }

    @PutMapping("/{empresa}/inventarios/bodega")
    public Object ActualizaBodega(@RequestBody Inventarios p) {
        return obj.ActualizaBodega(p);
    }

    @GetMapping("/{empresa}/inventarios/folio")
    public Object GetFolio(@PathVariable("empresa") String empresa) {
        return obj.GetFolio(empresa);
    }

    @PutMapping("/{empresa}/inventarios/cortebodega")
    public Object CorteInventarioBodega(String nombreEmpresa, String id_sucursal, String id_bodega, String id_user, String id_empresa) {
        return obj.CorteInventarioBodega(nombreEmpresa, id_sucursal, id_bodega, id_user, id_empresa);
    }
    
    @GetMapping("/{empresa}/inventarios/cortexsucursal/{id_sucursal}/{id_usuario}/{id_bodega}")
    public Object CorteXsucursal(@PathVariable("empresa") String empresa ,  String nombreEmpresa,Integer sucursal, Integer id_usuario , Integer bodega) {
    	return obj.CorteXsucursal(nombreEmpresa, sucursal, id_usuario, bodega);
    }
    
    @GetMapping("/{empresa}/inventarios/corteinicial/{id_sucursal}/{id_usuario}/{id_bodega}")
    public Object CortePrimerInv(@PathVariable("empresa") String empresa ,  String nombreEmpresa,Integer sucursal, Integer id_usuario , Integer bodega) {
    	return obj.CortePrimerInv(nombreEmpresa,sucursal, id_usuario, bodega);
    }
    
    
    @PostMapping("/{empresa}/inventarios/corte_inventario")
    public Object corteInventario(@RequestBody corte_inventario obj,@PathVariable("empresa") String nombre_empresa){
                return this.obj.realizarCorteInventario(obj,nombre_empresa);
    }
}
