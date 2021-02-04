/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.Inventarios;
import com.entrecodigos.repositorios.Productos;
import com.entrecodigos.repositorios.control_movimientos;
import com.entrecodigos.repositorios.corte_inventario;
import com.entrecodigos.repositorios.insumos;
import com.entrecodigos.repositorios.ordenescompra;
import com.entrecodigos.repositorios.proveedores;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("objInventarios")
public class InventariosDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Object getInventario(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from inventario where id_empresa = %1$s order by categoria";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getBodega(String nombreEmpresa, int id_sucursal) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from bodegas where  id_empresa= %1$s";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getMedidas(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from medidas where  id_empresa= %1$s";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getBodegaEspecifico(String nombreEmpresa, int id_bodega) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select nombre_bodega from bodegas where  id_bodega= %1$s";
        query = String.format(query, id_bodega);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getDesgloseFolioOrden(String nombreEmpresa, int folio) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "SELECT a2.descripcion ,a1.cantidad,a1.unidad_medida FROM orden_compra_detalle a1 join inventario a2 ON a1.id_inventario=a2.id_inventario   where a2.id_empresa=" + idEmpresa + " and a1.folio=" + folio + "";
        query = String.format(query);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getProveedores(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from proveedores where id_empresa = %1$s ";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getOrdenesCompra(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from ordenes_compra where id_empresa = %1$s order by folio desc ";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getHistorial(String id_inventario, String nombreEmpresa, int id_sucursal, int id_ubicacion) {
        Object resultado;
        try {
            String queryempresa = "select id from empresas where usuario = '%1$s'";
            queryempresa = String.format(queryempresa, nombreEmpresa);
            Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
            int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
            String query = "";
            if (id_ubicacion == 0) {
                query = "SELECT *,a1.cantidad as cantidad_historial FROM control_movimientos a1 INNER JOIN inventario a2 ON a1.id_inventario = a2.id_inventario WHERE a1.id_inventario = %1$s and a1.id_empresa=%2$s  and a1.id_sucursal=%3$s and a1.ubicacion = '0' and id_corte is null ORDER BY a1.fecha asc;";
                query = String.format(query, id_inventario, idEmpresa, id_sucursal);

            } else {
                query = "SELECT a2.*,a1.cantidad as cantidad_historial FROM control_movimientos a1 LEFT JOIN inventario a2 ON a1.id_inventario = a2.id_inventario WHERE a1.id_inventario = %1$s and tipo_mov <> 'P' and a1.id_empresa=%2$s  and a1.ubicacion = %3$s ORDER BY a1.fecha asc;";
                query = String.format(query, id_inventario, idEmpresa, id_ubicacion);
            }
            resultado = jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object getInventarioespecifico(String descripcion) {
        Object resultado;
        try {
            String query = "SELECT * FROM inventario where descripcion like '" + descripcion + "%'";
            resultado = jdbcTemplate.queryForList(query);
        } catch (Exception e) {
            Map<String, Object> mensaje = new HashMap<String, Object>();
            mensaje.put("respuesta", "Valor no encontrado en la base de datos");
            resultado = mensaje;
        }
        return resultado;
    }

    public Object DeleteInventario(Integer id) {
        String query = "delete from inventario where id_inventario=" + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object DeleteMedida(Integer id) {
        String query = "delete from medidas where id=" + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object DeleteBodega(Integer id) {
        String query = "delete from bodegas where id_bodega=" + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object DeleteProveedor(Integer id) {
        String query = "delete from proveedores where id_proveedor=" + id;
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Registro Eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar registro");
        }
        return resultado;
    }

    public Object ActualizarInventario(Inventarios p) {
        return null;

    }

    public Object InsertaInventario(Inventarios p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into inventario(unidad_medida,descripcion,    ubicacion,    categoria,   minimo,id_empresa,codigo,   id_proveedor,costo_unitario) values "
                + " ('%1$s',   upper('%2$s'),      '%3$s',    '%4$s',      %5$s,    %6$s,      '%7$s',  %8$s,   %9$s) returning id_inventario ";
        query = String.format(query, p.getUnidad_medida(), p.getDescripcion(), p.getUbicacion(), p.getCategoria(), p.getMinimo(), idEmpresa, p.getCodigo(), p.getId_proveedor(), p.getCosto_unitario());

        Map<String, Object> resultado = jdbcTemplate.queryForMap(query);

        resultado.put("respuesta", "Registro insertado");

        return resultado;
    }

    public Object AjusteMovimiento(List<control_movimientos> p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "";
        for (control_movimientos item : p) {

            String aux = " insert into control_movimientos (cantidad,tipo_mov,id_inventario,ubicacion,id_sucursal,id_usuario,observaciones,fecha,hora,id_empresa) values"
                    + " (%1$s,'%2$s',%3$s,'%4$s',%5$s,%6$s,'%7$s',(now() AT TIME ZONE 'America/Mexico_City'),((now() AT TIME ZONE 'America/Mexico_City')::TIME),%8$s); ";

            aux = String.format(aux, item.getCantidad(), item.getTipo_mov(), item.getId_inventario(), item.getUbicacion(), item.getId_sucursal(), item.getId_usuario(), item.getObservaciones(), idEmpresa);

            query += aux;
        }
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registros insertador correctamente");

        return resultado;
    }

    public Object InsertarProveedor(proveedores p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into proveedores (nombre , descripcion , domicilio , rfc , correo , telefono , notas , id_empresa ) values (upper('%1$s'), upper('%2$s'), upper('%3$s'), upper('%4$s') , upper ('%5$s') , %6$s , upper ('%7$s'), %8$s);";
        query = String.format(query, p.getNombre(), p.getDescripcion(), p.getDomicilio(), p.getRfc(), p.getCorreo(), p.getTelefono(), p.getNotas(), idEmpresa);
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registros insertador correctamente");

        return resultado;
    }

    public Object InsertarBodega(Inventarios p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into  bodegas (nombre_bodega,ubicacion_bodega,responsable_bodega,tiempo_real,id_empresa,id_sucursal) values (upper('%1$s'),upper('%2$s'),upper ('%3$s'),%4$s,%5$s,%6$s) ;";
        query = String.format(query, p.getNombre_bodega(), p.getUbicacion_bodega(), p.getResponsable_bodega(), p.getTiempo_real(), idEmpresa, p.getId_sucursal());
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registros insertador correctamente");

        return resultado;
    }

    public Object InsertarMedida(Inventarios p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into  medidas (nombre,id_empresa) values (upper('%1$s'),%2$s) ;";
        query = String.format(query, p.getNombre(), idEmpresa);
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registros insertador correctamente");

        return resultado;
    }

    public Object CorteProducto(int id_inventario, int id_sucursal, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "SELECT SUM (cantidad) AS cantidad FROM ( SELECT COALESCE (SUM(cantidad), 0) AS cantidad	FROM control_movimientos WHERE	tipo_mov IN ('E', 'OC') AND id_inventario = %1$s AND id_sucursal = %2$s UNION SELECT((COALESCE(SUM(cantidad), 0))*- 1) AS cantidad	FROM control_movimientos WHERE	tipo_mov IN ('S', 'V')AND id_inventario = %1$s AND id_sucursal = %2$s AND id_empresa=%3$s) AS A1";
        query = String.format(query, id_inventario, id_sucursal, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object ActualizaProveedor(proveedores p) {
        String query = "update  proveedores set nombre=upper('%1$s') , descripcion=upper('%2$s') , domicilio=upper('%3$s')  , rfc = upper('%4$s')   , correo=upper('%5$s')   , telefono='%6$s'  , notas=upper('%7$s') where id_proveedor=%8$s";
        query = String.format(query, p.getNombre(), p.getDescripcion(), p.getDomicilio(), p.getRfc(), p.getCorreo(), p.getTelefono(), p.getNotas(), p.getId_proveedor());
        Map<String, Object> resultado = new HashMap<String, Object>();
        jdbcTemplate.update(query);
        resultado.put("respuesta", "hecho");
        return resultado;

    }

    public Object ActualizaBodega(Inventarios p) {
        String query = "update  bodegas set nombre=upper('%1$s') ,ubicacion_bodega=upper('%2$s'), responsable_bodega=upper('%3$s'), tiempo_real=%4$s where id_bodega=%5$s";
        query = String.format(query, p.getNombre_bodega(), p.getUbicacion_bodega(), p.getResponsable_bodega(), p.getTiempo_real(), p.getId_bodega());
        Map<String, Object> resultado = new HashMap<String, Object>();
        jdbcTemplate.update(query);
        resultado.put("respuesta", "hecho");
        return resultado;

    }

    public Object GetFolio(String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "SELECT max(folio) +1 as ultimo  FROM ordenes_compra where id_empresa=" + idEmpresa + ";";
        Map<String, Object> folio = jdbcTemplate.queryForMap(query);
        String valida = (String.valueOf(folio.get("ultimo")));
        int obtenido;
        if (valida == "null") {
            obtenido = 1;
        } else {
            obtenido = Integer.parseInt(String.valueOf(folio.get("ultimo")));

        }
        //  obtenido= Integer.parseInt(String.valueOf( folio.get("ultimo")));

        return obtenido;
    }

    public Object InsertaOrden(String nombreEmpresa, Inventarios a) {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            String query = "insert into ordenes_compra (folio , fecha , elaboro,id_empresa, ubicacion)  values (" + a.getFolio() + "," + "(now() AT TIME ZONE 'America/Mexico_City')" + ",'" + a.getElaboro() + "'," + idEmpresa + "," + a.getUbicacion() + ")";
            jdbcTemplate.update(query);
            List<ordenescompra> arreglo = a.getOrdenescompra();
            for (ordenescompra item : arreglo) {
                query = "insert into orden_compra_detalle (folio , id_inventario, unidad_medida , cantidad , id_empresa) values (" + a.getFolio() + "," + item.getId_inventario() + ",'" + item.getUnidad_medida() + "'," + item.getCantidad() + "," + idEmpresa + ");";
                jdbcTemplate.update(query);

                String query1 = "insert into control_movimientos (cantidad,tipo_mov,id_inventario,ubicacion,id_sucursal,id_usuario,fecha,hora,id_empresa) values (" + item.getCantidad() + ",'OC'," + item.getId_inventario() + "," + a.getUbicacion() + "," + a.getId_sucursal() + "," + a.getId_usuario() + ",(now() AT TIME ZONE 'America/Mexico_City'),((now() AT TIME ZONE 'America/Mexico_City')::TIME)," + idEmpresa + ")";
                jdbcTemplate.update(query1);
            }
            resultado.put("respuesta", "Registro insertado");

        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar");
        }

        return resultado;
    }

    public Object CorteInventarioBodega(String nombreEmpresa, String id_sucursal, String id_bodega, String id_user, String id_empresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String quer = "SELECT DISTINCT(id_inventario) , descripcion, unidad_medida FROM inventario where id_bodega=" + id_sucursal + " and id_empresa=" + nombreEmpresa;
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(quer);

        for (Map<String, Object> item : resultado) {
            String id_inventario = (String.valueOf(item.get("ultimo")));

            String inserta = "insert into control_movimientos (cantidad , tipo_mov, id_inventario,ubicacion,id_sucursal,id_usuario,observaciones,fecha,hora,id_empresa) VALUES (0,'C'," + id_inventario + "," + id_bodega + "," + id_sucursal + ", " + id_user + ",'CORTE',current_date,current_time," + id_empresa + ");";
            jdbcTemplate.update(inserta);
        }

        return resultado;
    }

    public Object realizarCorteInventario(corte_inventario obj, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "insert into corte_inventario (id_inventario,cantidad_real,cantidad_programa,fecha,hora,id_usuario,id_ubicacion,id_sucursal,id_empresa) values (%1$s,%2$s,%3$s,(now() AT TIME ZONE 'America/Mexico_City'),((now() AT TIME ZONE 'America/Mexico_City')::TIME),%4$s,%5$s,%6$s,%7$s) returning id_corte";
        query = String.format(query, obj.getId_inventario(), obj.getCantidad_real(), obj.getCantidad_programa(), obj.getId_usuario(), obj.getId_ubicacion(), obj.getId_sucursal(), idEmpresa);

        Map<String, Object> mapa = jdbcTemplate.queryForMap(query);

        int id_corte = Integer.parseInt(String.valueOf(mapa.get("id_corte")));

        if (obj.getId_ubicacion() == 0) {
            query = "update control_movimientos set id_corte = %1$s where id_empresa = %2$s and ubicacion = '%3$s' and id_sucursal = %4$s and id_inventario = %5$s and id_corte is null";
            query = String.format(query, id_corte, idEmpresa, obj.getId_ubicacion(), obj.getId_sucursal(),obj.getId_inventario());
        } else {
            query = "update control_movimientos set id_corte = %1$s where id_empresa = %2$s and ubicacion = '%3$s' and id_inventario = %4$s and id_corte is null";
            query = String.format(query, id_corte, idEmpresa, obj.getId_ubicacion(),obj.getId_inventario());
        }

        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registro actualizado");
        resultado.put("id_corte",id_corte);

        return resultado;

    }
    
    
public Object CortePrimerInv(String nombreEmpresa, Integer sucursal, Integer id_usuario , Integer bodega) {
	
    String queryempresa = "select id from empresas where usuario = '%1$s'";
    queryempresa = String.format(queryempresa, nombreEmpresa);
    Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
    int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
		
		String query = "SELECT * FROM control_movimiento where tipo_mov='CG' and ubicacion =%1$s and id_sucursal=%2$s";
		query= String.format(query ,sucursal );
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        if (resultado.isEmpty())
        {
        
        	String inserta = "insert into  control_movimientos (cantidad , tipo_mov , id_inventario , ubicacion , id_sucursal , id_usuario , observaciones , fecha , hora , id_empresa)\r\n" + 
        			"values (0 , 'CG' , 0 , %1$s , %2$s , %3$s , 'corte general' , current_date  , current_time , %4$s);";
        	inserta = String.format(inserta , bodega , sucursal , id_usuario);
        	this.jdbcTemplate.update(inserta);
        	
        	
        }
return null;
}



public Object CorteXsucursal (String nombreEmpresa, Integer sucursal, Integer id_usuario , Integer bodega) {
	
	String inserta = "insert into  control_movimientos (cantidad , tipo_mov , id_inventario , ubicacion , id_sucursal , id_usuario , observaciones , fecha , hora , id_empresa)\r\n" + 
			"values (0 , 'CG' , 0 , %1$s , %2$s , %3$s , 'corte general' , current_date  , current_time , %4$s);";
	inserta = String.format(inserta , bodega , sucursal , id_usuario);
	this.jdbcTemplate.update(inserta);
	
	String query ="SELECT id_control FROM control_movimiento where tipo_mov='C' and id_sucursal =%1$s ORDER BY id_control desc limit 2;";
	query = String.format(query , sucursal);
    List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
    String fecha2= resultado.get(0).toString();
    String fecha1= resultado.get(1).toString();
    


	return null;
	
}
	

}
