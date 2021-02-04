/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.empresas;
import com.entrecodigos.repositorios.usuarios_administrativos;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author samv
 */
@Transactional
@Component("objEmpresas")
public class empresasDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object gets() {
        String query = "select * from empresas order by id";

        return jdbcTemplate.queryForList(query);
    }

    public Object getEspecifico(int id) {
        String query = "select * from empresas where id = %1$s order by id";

        query = String.format(query, id);

        return jdbcTemplate.queryForList(query);
    }

    public Object insertar(empresas e) {

        String query = "insert into empresas (nombre,descripcion,domicilio,usuario,password,activo,inicio,fin,plan) values (?,?,?,?,?,?,?,?,?) returning id;";
        Map<String,Object> respu = jdbcTemplate.queryForMap(query, new Object[]{e.getNombre(), e.getDescripcion(), e.getDomicilio(), e.getUsuario(), e.getPassword(), true, e.getInicio(), e.getFin(), e.getPlan()});
        
        int id_empresa = Integer.parseInt(String.valueOf(respu.get("id")));
        
        query = "insert into sucursales(nombre,id_empresa) values ('DEFAULT',%1$s) RETURNING id";
        query = String.format(query, id_empresa);
        
        respu = jdbcTemplate.queryForMap(query);
        int id_sucursal = Integer.parseInt(String.valueOf(respu.get("id")));
        
        query = " insert into users (id_empresa,id_sucursal,nombre,login,password,menu,catalogos,bar,cocina,cuentas,transacciones,inicio,gestion_usuarios,sucursales,mesas,caja,productos,historial_cuentas,reportes,autorizar,inventario,configuraciones,iniciar_en) "+
                " values (%1$s,%2$s,'DEFAULT','Admin','entrecodigos',true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,1) ";
        query = String.format(query,id_empresa,id_sucursal);
        
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("resultado", "insertado correctamente");

        return resultado;
    }

    public Object modificar(empresas e) {
        String query = "update empresas set nombre = ?,descripcion = ?,domicilio = ?,usuario= ?,password = ?,activo = ?,inicio = ? ,fin = ?, plan = ? where id = ?";
        jdbcTemplate.update(query, new Object[]{e.getNombre(), e.getDescripcion(), e.getDomicilio(), e.getUsuario(), e.getPassword(), true, e.getInicio(), e.getFin(), e.getPlan(), e.getId()});
        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("resultado", "insertado correctamente");

        return resultado;
    }

    public Object eliminar(int id) {
        String query = "delete from empresas where id = " + id;
        jdbcTemplate.update(query);
        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("resultado", "insertado correctamente");

        return resultado;
    }

    public Object login(usuarios_administrativos usuario) {

        String query = "select * from usuarios_administrativos where usuario = '%1$s'";
        query = String.format(query, usuario.getUsuario());
        Map<String, Object> resultado = jdbcTemplate.queryForMap(query);

        boolean entro = false;
        if (!resultado.isEmpty()) {
            String pass = String.valueOf(resultado.get("password"));
            if (usuario.getPassword().equalsIgnoreCase(pass)) {
                entro = true;
                resultado.put("entra", entro);
            }
        }
        return resultado;
    }

    public Object loginEmpresa(empresas usuario) {
        String query = "select * from empresas where usuario = '%1$s' and password = '%2$s'";
        query = String.format(query, usuario.getUsuario(),usuario.getPassword());
        Map<String, Object> resultado = jdbcTemplate.queryForMap(query);
        resultado.put("entra", true);
        return resultado;
    }

    public Object version() {
        String query = "select * from version;";
        return jdbcTemplate.queryForMap(query);
    }
}
