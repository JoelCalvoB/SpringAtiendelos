/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.controllers;

import com.entrecodigos.repositorios.notificacionMensaje;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 *
 * @author samv
 */
@Controller
public class notificacionesSocketsController {

    @MessageMapping("/pedir.cancelacion")
    @SendTo("/suscribirse/obtenercancelaciones_meseros")
    public notificacionMensaje realizarCancelacion(@Payload notificacionMensaje mensaje) {
        return mensaje;
    }
    
    @MessageMapping("/autorizar.cancelacion")
    @SendTo("/suscribirse/obtenercancelaciones_autorizados")
    public notificacionMensaje realizarCancelacionAutorizados(@Payload notificacionMensaje mensaje) {
        return mensaje;
    }

    @MessageMapping("/notificacion.agregarusuario")
    @SendTo("/suscribirse/cancelacion")
    public notificacionMensaje setUsuario(@Payload notificacionMensaje mensaje,
            SimpMessageHeaderAccessor headerAccessor) {
        
        headerAccessor.getSessionAttributes().put("usuario", mensaje.getUsuario());
        return mensaje;
        
    }

}
