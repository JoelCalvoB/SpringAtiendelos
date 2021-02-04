/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.daoimp.TicketsDaoImp;
import com.entrecodigos.repositorios.Detalle_Tickets;
import com.entrecodigos.repositorios.Tickets;

import java.util.ArrayList;
import java.util.List;
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

@RestController
@RequestMapping("/catalogos")

public class catalogosTicketsController {

    @Autowired
    @Qualifier("objTicketsDao")

    private TicketsDaoImp obj;

    @GetMapping("/(empresa)/tickets/sucursal/{identificador}")
    public Object getTicketSucursal(@PathVariable("identificador") String id,@PathVariable("empresa")String nombreEmpresa) {
        return obj.getTicketSucursal(id,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/tickets/sucursal/{id_sucursal}/mesa/{id_mesa}")
    public Object getTicketEspecifico(@PathVariable("id_sucursal") int idSucursal,@PathVariable("id_mesa") int idMesa,@PathVariable("empresa") String nombreEmpresa){
         return obj.getTicketEspecifico(idSucursal,idMesa,nombreEmpresa);
    }

    @PostMapping("/{empresa}/tickets/{unir}")
    public Object NuevoTicket(@RequestBody Tickets obj, @PathVariable("unir") boolean unir,@PathVariable("empresa") String nombreEmpresa) throws Exception {
        return this.obj.insertar(obj,unir,nombreEmpresa);
    }
    
     @PostMapping("/{empresa}/tickets/alias")
    public Object NuevoTicketAlias(@RequestBody Tickets obj,@PathVariable("empresa") String nombreEmpresa) {
        return this.obj.insertarAlias(obj,nombreEmpresa);
    }

    @DeleteMapping("/{empresa}/tickets/cancelar/{id}")
    public Object CancelarTicket(@PathVariable("id") String id,@PathVariable("empresa") String nombreEmpresa) {
        return this.obj.CancelarTicket(id,nombreEmpresa);
    }

    @PostMapping("/tickets/detalle")
    public Object insertarDetalleTicket(@RequestBody Detalle_Tickets obj) {
        return this.obj.insertarDetalleTickets(obj);
    }
    
    @PostMapping("/{empresa}/tickets/detalle/lista")
    public Object insertarDetalleTicketLista(@RequestBody List<Detalle_Tickets> obj,@PathVariable("empresa") String nombreEmpresa) throws Exception {
        return this.obj.insertarDetalleTicketsLista(obj,nombreEmpresa);
    }

    @PutMapping("/{empresa}/tickets/detalle")
    public Object actualizarDetalleTicket(@RequestBody Detalle_Tickets obj,@PathVariable("empresa") String nombreEmpresa) {
        return this.obj.actualizarDetalleTicket(obj,nombreEmpresa);
    }
    
    @PutMapping("/{empresa}/tickets/detalle/lista")
    public Object actualizarDetalleTicketArray(@RequestBody List<Detalle_Tickets> obj,@PathVariable("empresa") String nombreEmpresa) {
    	List<Object> listaDetalle = new ArrayList<Object>();
    	
    	for(Detalle_Tickets item : obj) {
    		listaDetalle.add(this.obj.actualizarDetalleTicket(item,nombreEmpresa));
    	}
    	
        return listaDetalle;
    }

    @GetMapping("/{empresa}/tickets/detalle/{identificador}")
    public Object obtenerDetalle(@PathVariable("identificador") int id,@PathVariable("empresa")String nombreEmpresa) {
        return obj.obtenerDetalleTickets(id,nombreEmpresa);
    }

    @GetMapping("/{empresa}/tickets/detalle/agrupado/{identificador}")
    public Object obtenerDetalleAgrupado(@PathVariable("identificador") String id,@PathVariable("empresa") String nombreEmpresa) {
        return obj.obtenerDetalleTicketsAgrupados(id,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/tickets/detalle/agrupado/ticketfinal/{identificador}")
    public Object obtenerDetalleAgrupadoticketfinal(@PathVariable("identificador") String id,@PathVariable("empresa")  String nombreEmpresa) {
        return obj.obtenerDetalleAgrupadoticketfinal(id,nombreEmpresa);
    }
    
    
       @GetMapping("/{empresa}/tickets/detalle/agrupadoagrupadoespecial/ticketfinal/{identificador}")
    public Object obtenerDetalleAgrupadoticketfinalEspecial(@PathVariable("identificador") String id,@PathVariable("empresa")  String nombreEmpresa) {
        return obj.obtenerDetalleAgrupadoticketfinalEspecial(id,nombreEmpresa);
    }

    @PutMapping("/{empresa}/tickets/cobrado")
    public Object cobrarTicket(@RequestBody Tickets obj,@PathVariable("empresa") String nombreEmpresa) {
        return this.obj.cobrarTicket(obj,nombreEmpresa);
    }

    @GetMapping("/{empresa}/tickets/notificaciones/{id_sucursal}/lugar/{lugar}")
    public Object obtenerdetallecocinero(@PathVariable("id_sucursal") int id_sucursal,@PathVariable("lugar") int lugar,@PathVariable("empresa")String nombreEmpresa) {
        return obj.obtenerdetallecocinero(id_sucursal,lugar,nombreEmpresa);
    }
    
    @GetMapping("/{empresa}/tickets/sucursal/{id_sucursal}/fecha/{fecha}")
    public Object getCanceladosTickets(@PathVariable("id_sucursal")String id_sucursal,@PathVariable("fecha")String fecha,@PathVariable("empresa") String nombreEmpresa){
       return obj.getCanceladosTickets(id_sucursal,fecha,nombreEmpresa);
    }
    
   

    @PutMapping("/{empresa}/tickets/detallecocinero")
    public Object actualizardetallecocinero(@RequestBody Detalle_Tickets obj,@PathVariable("empresa")String nombreEmpresa) {
        return this.obj.actualizardetallecocinero(obj,nombreEmpresa);
    }
    
    @DeleteMapping("/{empresa}/tickets/detalle/{identificador}")
    public Object eliminarDetalleTicket(@PathVariable("identificador") String id,@PathVariable("empresa") String nombreEmpresa){
      return obj.eliminarDetalleTicket(id,nombreEmpresa);
    }
    //Dirección de parte de las notificaciones.....
    @GetMapping("/{empresa}/tickets/notificacion")
    public Object getNotificacion(@PathVariable("empresa")String nombreEmpresa) {
        return this.obj.getNotificaciones();
    }
    
    @GetMapping("/{empresa}/tickets/mesas/{idsucursal}/mesero/{idmesero}")
    public Object getMesasTickets(@PathVariable("idsucursal") int idSucursal,@PathVariable("idmesero") int id_mesero,@PathVariable("empresa")String nombreEmpresa){
        return obj.getMesasTickets(idSucursal,id_mesero,nombreEmpresa);
    }
    
    
    @GetMapping("/{empresa}/tickets/corteespecial/sucursal/{id_sucursal}/fecha/{fecha}")
    public Object GetDesgloseCajaEspecial (@PathVariable("id_sucursal") int id_sucursal,@PathVariable("fecha")String fecha,@PathVariable("empresa") String nombreEmpresa){
        return obj.GetDesgloseCajaEspecial(id_sucursal, fecha,nombreEmpresa);
    }
    
    
     @DeleteMapping("/{empresa}/tickets/borrarespecial/sucursal/{id_sucursal}/fecha/{fecha}")
    public Object DeleteDesgloseCajaEspecial (@PathVariable("id_sucursal") int id_sucursal,@PathVariable("fecha")String fecha,@PathVariable("empresa") String nombreEmpresa){
        return obj.DeleteDesgloseCajaEspecial(id_sucursal, fecha,nombreEmpresa);
    }
    
     @GetMapping("/{empresa}/tickets/detaleespecial/sucursal/{id_sucursal}/fecha/{fecha}")
    public Object GetDesgloseCorteEspecial (@PathVariable("id_sucursal") int id_sucursal,@PathVariable("fecha")String fecha,@PathVariable("empresa") String nombreEmpresa){
        return obj.GetDesgloseCorteEspecial(id_sucursal, fecha,nombreEmpresa);
    }
    
     @PostMapping("/{empresa}/tickets/cortecierreespecial")
    public Object CierraCorteEspecial(@RequestBody Tickets p, @PathVariable("empresa") String empresa  ) {
        return this.obj.CierraCorteEspecial(p, empresa);
    }
    
    
    @PutMapping("/{empresa}/tickets/cambiarmesa/{id_ticket}/{id_usuario}")
    public Object cambiarMeseroTicket(@PathVariable("id_ticket") int idTicket,@PathVariable("id_usuario") int idUsuario,@PathVariable("empresa") String nombreEmpresa){
      return this.obj.cambiarMeseroTicket(idTicket,idUsuario,nombreEmpresa);
    }
    
    @PutMapping("/{empresa}/tickets/cambiarmesa/{unir}")
    public Object cambiarMeseroTicket(@RequestBody Tickets obj,@PathVariable("unir") boolean unir,@PathVariable("empresa") String nombreEmpresa){
      return this.obj.cambiarMesaTicket(obj,unir,nombreEmpresa);
    }
    
    
    @PostMapping("/{empresa}/tickets/descuento")
    public Object aplicandoDescuentoTicket(@RequestBody Tickets obj,@PathVariable("empresa") String nombreEmpresa){
            return this.obj.aplicandoDescuentos(obj,nombreEmpresa);
    }
    
    
      @PostMapping("/{empresa}/tickets/mesaseparada/{id_ticket}/partir/{numero}")
    public Object aplicandoDescuentoTicket(@PathVariable("id_ticket") String id_ticket,@PathVariable("numero") int numero,@PathVariable("empresa") String nombreEmpresa){
            return this.obj.mesasSeparadas(id_ticket,numero,nombreEmpresa);
    }
    
    
    @GetMapping("/{empresa}/tickets/mesas/cantidad/{id_sucursal}/mesero/{id_mesero}")
    public Object getCantidadMesas (@PathVariable("id_sucursal") int id_sucursal,@PathVariable("id_mesero") int id_mesero,@PathVariable("empresa") String nombreEmpresa){
        return obj.getCantidadMesas(id_sucursal,id_mesero,nombreEmpresa);
    }
    
     @GetMapping("/{empresa}/tickets/ticket/activo/{id_ticket}")
    public Object getticketactivo (@PathVariable("id_ticket") int idTicket,@PathVariable("empresa") String nombreEmpresa){
        return obj.obtenerTicketactivo(idTicket,nombreEmpresa);
    }
    
     @PostMapping("/{empresa}/tickets/productos/cambiarMesa/{id_ticket}")
    public Object cambiarMesaProducto (@RequestBody Detalle_Tickets detalle,@PathVariable("id_ticket")int id_ticket,@PathVariable("empresa") String nombreEmpresa){
        return obj.cambiarMesaProducto(detalle,id_ticket,nombreEmpresa);
    }
    
      @PostMapping("/{empresa}/tickets/{id_ticket}/unircuentas")
    public Object unircuentas (@RequestBody List<Tickets> lista,@PathVariable("id_ticket")int id_ticket,@PathVariable("empresa") String nombreEmpresa){
        return obj.unircuentas(lista,id_ticket,nombreEmpresa);
    }
    
    
     @PostMapping("/{empresa}/tickets/{id_ticket}/separaritems")
    public Object separaritems (@RequestBody List<Detalle_Tickets> lista,@PathVariable("id_ticket")int id_ticket,@PathVariable("empresa") String nombreEmpresa){
        return obj.separaritems(lista,id_ticket,nombreEmpresa);
    }
     
     @PostMapping("/{empresa}/tickets/actualizamesa")
     public Object actualizarNombreMesa(@RequestBody Tickets p,@PathVariable("empresa") String nombreEmpresa){
         return this.obj.actualizamesa(p, nombreEmpresa);
     }
}
