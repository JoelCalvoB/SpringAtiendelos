/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.eventos;

import com.entrecodigos.repositorios.notificacionMensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 * @author samv
 */
@Component
public class WebSocketEventListener {
     private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Nuevo usuario conectado al socket");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String usuario = (String) headerAccessor.getSessionAttributes().get("usuario");
        if(usuario != null) {
            logger.info("Usuario desconectado al websocket : " + usuario);

            notificacionMensaje obj = new notificacionMensaje();
            obj.setTipoMensaje(notificacionMensaje.TipoMensaje.CERRAR_SESION);
            obj.setUsuario(usuario);

            messagingTemplate.convertAndSend("/topic/public", obj);
        }
    }
}
