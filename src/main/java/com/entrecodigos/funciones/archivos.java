/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.funciones;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;


/**
 *
 * @author samv
 */

public class archivos {
    
    
    public BufferedImage decodificarImagenBase64(String imageString){
       BufferedImage image = null;
        byte[] imageByte;
        try {
//            BASE64Decoder decoder = new BASE64Decoder();
//            imageByte = decoder.decodeBuffer(imageString);
//            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//            image = ImageIO.read(bis);
//            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public void escribirArchivo(BufferedImage obj,int id_producto){
        try {
            String rutaUsuario = System.getProperty("user.dir");
            String ruta = String.format(rutaUsuario+"\\src\\main\\resources\\static\\imgs\\%1$s.jpg", id_producto);
            File outputfile = new File(ruta);
            ImageIO.write(obj, "png", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
