/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entrecodigos.daoimp;

import com.entrecodigos.repositorios.Detalle_Tickets;
import com.entrecodigos.repositorios.Productos;
import com.entrecodigos.repositorios.Promociones;
import com.entrecodigos.repositorios.Tickets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Component("objTicketsDao")
public class TicketsDaoImp {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private int notificar = 0;

    public TicketsDaoImp() {
        System.out.println("este es el contrusctor");
    }

    public Object getTicketSucursal(String id, String nombreEmpresa) {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from tickets where id_sucursal = " + id + " and id_empresa = " + id;
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getTicketEspecifico(int idSucursal, int idMesa, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from tickets where id_sucursal = ? and id_mesa = ? and id_empresa = ?";
        Map<String, Object> resultado = jdbcTemplate.queryForMap(query, new Object[]{idSucursal, idMesa, idEmpresa});
        return resultado;
    }

    public Object insertar(Tickets p, boolean unir, String nombreEmpresa) throws Exception {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        int id_user = p.getIdUser();
        int id_sucursal = p.getIdSucursal();
        int id_mesa = p.getIdMesa();
        String nombre = p.getNombre();

        String query = String.format("update mesas set ocupada = true where id_sucursal = %1$s and id_mesa = %2$s and id_empresa = %3$s and ocupada = false returning * ", id_sucursal, id_mesa, idEmpresa);
        query = String.format(query, id_sucursal, id_mesa, idEmpresa);

        try {
            Map<String, Object> mesas = jdbcTemplate.queryForMap(query);
        } catch (Exception e) {
            throw new Exception("Mesa ocupada por otro mesero");
        }

        Object resultado = null;
        try {

            query = "select COALESCE(max(id_folio),0)  as folio from historico_tickets where id_sucursal=? and id_empresa = ?";
            Map<String, Object> maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

            String maximoFolio = maximo.get("folio").toString();
            int intfolio = Integer.parseInt(maximoFolio);

            query = "select COALESCE(max(id_folio),0)  as folio from tickets where id_sucursal=? and id_empresa = ?";
            maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

            maximoFolio = maximo.get("folio").toString();

            int intfolio2 = Integer.parseInt(maximoFolio);

            intfolio = (intfolio >= intfolio2) ? intfolio : intfolio2;

            intfolio++;

            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,%1$s) returning *;";
            query = String.format(query, idEmpresa);

            Map<String, Object> respu = jdbcTemplate.queryForMap(query);
            String id_tiket = String.valueOf(respu.get("id_ticket"));
            String nombreTicket = String.valueOf(respu.get("nombre"));

            if (!unir) {
                query = String.format("update mesas set ocupada = true where id_sucursal = %1$s and id_mesa = %2$s and id_empresa = %3$s", id_sucursal, id_mesa, idEmpresa);
            } else {
                query = "";
                List<Integer> mesasAfectadas = p.getMesasAfectadas();
                for (Integer item : mesasAfectadas) {
                    query += String.format("update mesas set ocupada = true,mesafusionada = %3$s where id_sucursal = %1$s and id_mesa = %2$s and id_empresa = %4$s;", id_sucursal, item, id_mesa, idEmpresa);
                }

            }

            jdbcTemplate.update(query);

            Map<String, Object> r1 = new HashMap<String, Object>();
            r1.put("respuesta", "Ticket Ingresado Correctamente");
            r1.put("id_ticket", id_tiket);
            r1.put("id_mesa", id_mesa);
            r1.put("nombre", nombreTicket);

            resultado = r1;

        } catch (Exception e) {

            Map<String, Object> r1 = new HashMap<String, Object>();
            r1.put("respuesta", "Error al ingresar un ticket nuevo");
            resultado = r1;

        }

        return resultado;
    }

    public Object CancelarTicket(String id, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from tickets where id_ticket = %1$s and id_empresa = %2$s";
        query = String.format(query, id, idEmpresa);
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {

            resultado = this.jdbcTemplate.queryForMap(query);
            int id_ticket = Integer.parseInt(String.valueOf(resultado.get("id_ticket")));
            int id_user = Integer.parseInt(String.valueOf(resultado.get("id_user")));
            String fecha = String.valueOf(resultado.get("fecha"));
            int id_folio = Integer.parseInt(String.valueOf(resultado.get("id_folio")));
            int id_sucursal = Integer.parseInt(String.valueOf(resultado.get("id_sucursal")));
            String hora = String.valueOf(resultado.get("hora"));
            int id_mesa = Integer.parseInt(String.valueOf(resultado.get("id_mesa")));
            String nombre = String.valueOf(resultado.get("nombre"));

            query = String.format("insert into historico_tickets(id_ticket,id_user,fecha,id_folio,id_sucursal,id_mesa,total,cancelado,nombre,hora,id_empresa) values(%1$s,%2$s,'%3$s',%4$s,%5$s,%6$s,%7$s,%8$s,'%9$s',((now() AT TIME ZONE 'America/Mexico_City')::TIME),%10$s)",
                    id_ticket, id_user, fecha, id_folio, id_sucursal, id_mesa, 0, true, nombre, idEmpresa);

            this.jdbcTemplate.update(query);

            query = "select d.*,(p.precio*d.cantidad) as total from detalle_ticket d inner join productos p on p.id_producto = d.id_producto where id_ticket = " + id;
            List<Map<String, Object>> aux = this.jdbcTemplate.queryForList(query);
            query = "";
            for (Map<String, Object> item : aux) {
                id_ticket = Integer.parseInt(String.valueOf(item.get("id_ticket")));
                int id_producto = Integer.parseInt(String.valueOf(item.get("id_producto")));
                double cantidad = Double.parseDouble(String.valueOf(item.get("cantidad")));
                String observaciones = String.valueOf(item.get("observaciones"));
                boolean cancelado = Boolean.parseBoolean(String.valueOf(item.get("cancelado")));
                double total = Double.parseDouble(String.valueOf(item.get("total")));
                query += String.format("insert into historico_detalletickets values (%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s);",
                        id_ticket, id_producto, cantidad, observaciones, cancelado, total);
            }

            jdbcTemplate.update(query);

            query = "delete from tickets where id_ticket = " + id + " and id_empresa = " + idEmpresa;
            this.jdbcTemplate.update(query);

            query = " delete from detalle_ticket where id_ticket = " + id;
            this.jdbcTemplate.update(query);

            query = " update mesas set ocupada = false where id_mesa = ? and id_sucursal = ? and id_empresa = ?";
            this.jdbcTemplate.update(query, new Object[]{
                id_mesa, id_sucursal, idEmpresa
            });

            query = " update mesas set ocupada = false,mesafusionada = null where mesafusionada = ? and id_sucursal = ? and id_empresa = ?";
            this.jdbcTemplate.update(query, new Object[]{
                id_mesa, id_sucursal, idEmpresa
            });

        } catch (Exception e) {
            resultado.put("respuesta", "Registro  no cancelado");

        }

        return resultado;
    }

    public Object insertarDetalleTickets(Detalle_Tickets detalle) {
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            String query = "insert into detalle_ticket (id_ticket,id_producto,cantidad,servido,observaciones,tipo_producto,fecharegistro,cancelado) values("
                    + detalle.getId_ticket() + "," + detalle.getId_producto() + "," + detalle.getCantidad() + ",false,'" + detalle.getObservaciones() + "'," + detalle.getTipo_producto() + ",(now() AT TIME ZONE 'America/Mexico_City'),false);";

            jdbcTemplate.update(query);
            this.notificar++;
            this.notificar = (this.notificar > 10000) ? 0 : this.notificar;
            resultado.put("respuesta", "Productos Insertados a la Orden");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar productos a la orden");
        }
        return resultado;
    }

    public Object insertarDetalleTicketsLista(List<Detalle_Tickets> lista, String nombreEmpresa) throws Exception {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        if (lista.size() > 0) {
            Detalle_Tickets detalle = lista.get(0);
            int id_ticket
                    = detalle.getId_ticket();

            String query = "select id_ticket from tickets where id_ticket = %1$s and id_empresa = %2$s;";
            query = String.format(query, id_ticket, idEmpresa);

            Map<String, Object> resultado = jdbcTemplate.queryForMap(query);
        }

        Map<String, Object> resultado = new HashMap<String, Object>();
        for (Detalle_Tickets item : lista) {
            int id_pruducto = item.getId_producto();
            int id_sucursal = item.getId_sucursal();
            String queryInsumos = "select * from insumos s inner join inventario iin on iin.id_inventario = s.id_inventario  where s.id_producto = %1$s and s.id_sucursal = %2$s";
            queryInsumos = String.format(queryInsumos, id_pruducto, id_sucursal);
            List<Map<String, Object>> resultadoinsumos = jdbcTemplate.queryForList(queryInsumos);
            for (Map<String, Object> insumo : resultadoinsumos) {
                int id_inventario = Integer.parseInt(String.valueOf(insumo.get("id_inventario")));
                double cantidad = Double.parseDouble(String.valueOf(insumo.get("cantidad")));

                String queryControl = "select count(*) as cantidad from control_movimientos where tipo_mov = 'C' and id_inventario = %1$s and id_sucursal = %2$s";
                queryControl = String.format(queryControl, id_inventario, id_sucursal);

                Map<String, Object> mapaControl = jdbcTemplate.queryForMap(queryControl);

                int cantidadCortes = Integer.parseInt(String.valueOf(mapaControl.get("cantidad")));

                if (cantidadCortes == 0) {

                    queryControl = "select count(*) as cantidad from control_movimientos where tipo_mov = 'V' and id_inventario = %1$s and id_sucursal = %2$s";
                    queryControl = String.format(queryControl, id_inventario, id_sucursal);

                    mapaControl = jdbcTemplate.queryForMap(queryControl);

                    int cantidadProductos = Integer.parseInt(String.valueOf(mapaControl.get("cantidad")));

                    if (cantidadProductos == 0) {
                        queryControl = "insert into control_movimientos (cantidad,tipo_mov,id_inventario,ubicacion,id_sucursal,id_usuario,observaciones,fecha,hora)"
                                + " values (%1$s,'V',%2$s,'INVENTARIO',%3$s,%4$s,'CONSUMIDOS',(now() AT TIME ZONE 'America/Mexico_City'),(now() AT TIME ZONE 'America/Mexico_City')::time) returning *; ";

                        queryControl = String.format(queryControl, (0), id_inventario, id_sucursal, 0);

                        mapaControl = jdbcTemplate.queryForMap(queryControl);
                    }

                    queryControl = "select sum(cantidad) as cantidad from (select COALESCE(sum(cantidad),0) as cantidad from control_movimientos where tipo_mov in ('E','C') and id_inventario = %1$s and id_sucursal = %2$s"
                            + " union "
                            + " select ((COALESCE(sum(cantidad),0))*-1) as cantidad from control_movimientos where tipo_mov in ('S','V') and id_inventario = %1$s and id_sucursal = %2$s) a";

                    queryControl = String.format(queryControl, id_inventario, id_sucursal);

                } else {
                    queryControl = "select *  from control_movimientos where tipo_mov = 'C' and id_inventario = %1$s and id_sucursal = %2$s";
                    queryControl = String.format(queryControl, id_inventario, id_sucursal);

                    Map<String, Object> controlCorteMapa = jdbcTemplate.queryForMap(queryControl);

                    String fecha = String.valueOf(controlCorteMapa.get("fecha"));

                    queryControl = "select count(*) as cantidad from control_movimientos where tipo_mov = 'V' and id_inventario = %1$s and id_sucursal = %2$s and fecha >= '%3$s'";
                    queryControl = String.format(queryControl, id_inventario, id_sucursal, fecha);

                    mapaControl = jdbcTemplate.queryForMap(queryControl);

                    int cantidadProductos = Integer.parseInt(String.valueOf(mapaControl.get("cantidad")));

                    if (cantidad == 0) {
                        queryControl = "insert into control_movimientos (cantidad,tipo_mov,id_inventario,ubicacion,id_sucursal,id_usuario,observaciones,fecha,hora)"
                                + " values (%1$s,'V',%2$s,'INVENTARIO',%3$s,%4$s,'CONSUMIDOS',(now() AT TIME ZONE 'America/Mexico_City'),(now() AT TIME ZONE 'America/Mexico_City')::time) returning *; ";

                        queryControl = String.format(queryControl, 0, id_inventario, id_sucursal, 0);

                        mapaControl = jdbcTemplate.queryForMap(queryControl);
                    }

                    queryControl = "select sum(cantidad) as cantidad from (select COALESCE(sum(cantidad),0) as cantidad from control_movimientos where tipo_mov in ('E','C') and id_inventario = %1$s and id_sucursal = %2$s"
                            + " union "
                            + " select ((COALESCE(sum(cantidad),0))*-1) as cantidad from control_movimientos where tipo_mov in ('S','V') and id_inventario = %1$s and id_sucursal = %2$s) a";

                    queryControl = String.format(queryControl, id_inventario, id_sucursal);
                }

                Map<String, Object> mapaInventario = jdbcTemplate.queryForMap(queryControl);
                double cantidadNeto = Double.parseDouble(String.valueOf(mapaInventario.get("cantidad")));
                cantidadNeto = cantidadNeto - (cantidad * item.getCantidad());
                String nombreInventario = String.valueOf(mapaInventario.get("descripcion"));
                if (cantidadNeto < 0) {
                    throw new Exception("Ya no existen " + nombreInventario + " en el inventario.");
                }
            }
        }
        System.err.println("Se prepara para insertar");
        String aux = "";

        for (Detalle_Tickets detalle : lista) {
            String query = "insert into detalle_ticket (id_ticket,id_producto,cantidad,servido,observaciones,tipo_producto,fecharegistro,cancelado) values("
                    + detalle.getId_ticket() + "," + detalle.getId_producto() + "," + detalle.getCantidad() + "," + detalle.isServido() + ",'" + detalle.getObservaciones() + "'," + detalle.getTipo_producto() + ",(now() AT TIME ZONE 'America/Mexico_City'),false);";
            query = String.format(query);
            aux += query;
        }
        System.err.println("Ya inserto");
        if (!aux.equalsIgnoreCase("")) {
            System.err.println(aux);
            jdbcTemplate.update(aux);
            System.err.println("continua el proceso");
            aux = "";

            for (Detalle_Tickets item : lista) {
                int id_pruducto = item.getId_producto();
                int id_sucursal = item.getId_sucursal();
                String queryInsumos = "select * from insumos s inner join inventario iin on iin.id_inventario = s.id_inventario  where s.id_producto = %1$s and s.id_sucursal = %2$s";
                queryInsumos = String.format(queryInsumos, id_pruducto, id_sucursal);
                List<Map<String, Object>> resultadoinsumos = jdbcTemplate.queryForList(queryInsumos);
                for (Map<String, Object> insumo : resultadoinsumos) {
                    int id_inventario = Integer.parseInt(String.valueOf(insumo.get("id_inventario")));
                    double cantidad = Double.parseDouble(String.valueOf(insumo.get("cantidad")));

                    String query = "select id_control from control_movimientos where tipo_mov = 'V' order by fecha desc limit 1";
                    Map<String, Object> controlmov = jdbcTemplate.queryForMap(query);
                    String id_control = String.valueOf(controlmov.get("id_control"));

                    query = "update control_movimientos set cantidad = cantidad + (%1$s * %2$s) where id_control = %3$s ";
                    query = String.format(query, cantidad, item.getCantidad(), id_control);

                    jdbcTemplate.update(query);
                }
            }

            this.notificar++;
            this.notificar = (this.notificar > 10000) ? 0 : this.notificar;
        }
        resultado.put("respuesta", "Productos Insertados a la Orden");

        return resultado;
    }

    public Object obtenerDetalleTickets(int id, String nombreEmpresa) {

        String query = " SELECT	d.*,substr( (fecharegistro::TIME )::VARCHAR,1,8) as hora ,P .nombre, (case when  d.cortesia is null then p.precio * d.cantidad when d.cortesia = 1 then ((p.precio * d.cantidad) - d.efectivo_porcentaje) when d.cortesia = 2 then ((p.precio * d.cantidad)-(((p.precio * d.cantidad) * d.efectivo_porcentaje) / 100)) when d.cortesia = 3 then 0 END) as precio,d.cortesia,  p.precio as precio_unitario,  p.notificacion FROM	detalle_ticket d INNER JOIN productos P ON P .id_producto = d.id_producto WHERE "
                + " d.id_ticket = ?  ORDER BY	ID ASC ";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query, new Object[]{id});
        return resultado;
    }

    public Object obtenerDetalleTicketsAgrupados(String id, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select id_ticket,id_folio,cortesia_ticket,descontar_ticket, id_folio,id_producto,cortesia,nombre,unitario,sum(cantidad) as cantidad,sum(precio_total) as precio_total from productosView where id_ticket = %1$s and id_empresa = %2$s group  by id_ticket,id_producto,nombre,unitario,id_folio,cortesia,cortesia_ticket,descontar_ticket";
        query = String.format(query, id, idEmpresa);

        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        query = "select  (case "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Sun' then 'do' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Mon' then 'lu' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Tue' then 'ma' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Wed' then 'mi' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Thu' then 'ju' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Fri' then 'vi' "
                + " when (to_char((now() AT TIME ZONE 'America/Mexico_City'), 'Dy')) = 'Sat' then 'sa' "
                + " end) as dia ";

        Map<String, Object> queryDia = this.jdbcTemplate.queryForMap(query);
        String dia = String.valueOf(queryDia.get("dia"));

        query = "select * from promociones where dias like '%" + dia + "%' and (inicia::time) <= ((now() AT TIME ZONE 'America/Mexico_City')::time) and (termina::time) >= ((now() AT TIME ZONE 'America/Mexico_City')::time)";

        List<Map<String, Object>> promociones = jdbcTemplate.queryForList(query);

        List<Map<String, Object>> promocionesFinal = (List<Map<String, Object>>) new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> objetoEnlaPromocion = (List<Map<String, Object>>) new ArrayList<Map<String, Object>>();

        query = "update tickets set preticket = true where id_empresa = %1$s and id_ticket = %2$s";
        query = String.format(query, idEmpresa, id);
        jdbcTemplate.update(query);

        for (Map<String, Object> item : promociones) {
            String productosCodificado = String.valueOf(item.get("productoscodificado"));
            String[] split1 = productosCodificado.split("-");
            int totalPromociones = 0;
            boolean hayPromocion = false;
            int[][] promocion = new int[split1.length][2];
            int[][] productoPromocion = new int[split1.length][2];
            int x = 0;
            for (String i : split1) {
                String[] split2 = i.split(":");
                int id_producto = Integer.parseInt(split2[0]);
                int cantidad = Integer.parseInt(split2[1]);

                promocion[x][0] = id_producto;
                promocion[x][1] = cantidad;

                for (Map<String, Object> productos : resultado) {
                    int id_productoAux = Integer.parseInt(String.valueOf(productos.get("id_producto")));
                    if (id_productoAux == id_producto) {
                        hayPromocion = true;
                        int cantidadAux = Integer.parseInt(String.valueOf(productos.get("cantidad")));
                        productoPromocion[x][0] = id_productoAux;
                        productoPromocion[x][1] = cantidadAux;
                        Map<String, Object> mapaProducto = new HashMap<String, Object>();
                        mapaProducto.put("id", id_productoAux);
                        mapaProducto.put("nombre", productos.get("nombre"));
                        mapaProducto.put("cantidad", promocion[x][1]);
                        mapaProducto.put("total", productos.get("unitario"));
                        objetoEnlaPromocion.add(mapaProducto);
                        break;
                    }

                    hayPromocion = false;
                }

                x++;
            }
            if (hayPromocion) {
                int auxX = 0;
                while (true) {
                    boolean aplicaPromocion = false;
                    for (int auxX2 = 0; auxX2 < promocion.length; auxX2++) {
                        productoPromocion[auxX2][1] -= promocion[auxX2][1];
                        if (productoPromocion[auxX2][1] < 0) {
                            aplicaPromocion = false;
                            break;
                        }
                        aplicaPromocion = true;
                    }
                    if (aplicaPromocion) {
                        auxX++;
                    } else {
                        totalPromociones = auxX;
                        Map<String, Object> mapa = new HashMap<>();
                        mapa.put("promocion", item.get("nombre"));
                        mapa.put("totalPromocion", totalPromociones);
                        mapa.put("nombre", item.get("nombre"));
                        mapa.put("precio", item.get("precio"));
                        mapa.put("id_promocion", item.get("id"));

                        for (Map<String, Object> objAux : objetoEnlaPromocion) {
                            double total = Double.parseDouble(String.valueOf(objAux.get("total")));
                            int cantidad = Integer.parseInt(String.valueOf(objAux.get("cantidad")));

                            total = total * totalPromociones * cantidad;
                            cantidad = cantidad * totalPromociones;

                            objAux.remove("total");
                            objAux.remove("cantidad");

                            objAux.put("total", total);
                            objAux.put("cantidad", cantidad);
                        }

                        mapa.put("productos", objetoEnlaPromocion);
                        promocionesFinal.add(mapa);

                        objetoEnlaPromocion = (List<Map<String, Object>>) new ArrayList<Map<String, Object>>();
                        break;
                    }
                }

            }
        }

        Map<String, Object> resultadoFinal = new HashMap<String, Object>();
        resultadoFinal.put("resultado", resultado);
        resultadoFinal.put("promociones", promocionesFinal);

        return resultadoFinal;
    }

    public Object obtenerDetalleAgrupadoticketfinal(String id, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = " SELECT	t.id_ticket,t.id_mesa,t.iva,t.nombre as nombre_ticket,t.cortesia as cortesia_ticket,t.efectivo_porcentaje as descuento_ticket, t.id_folio,t.total,d.id_producto,d.id_producto,d.cortesia,d.efectivo_porcentaje,SUM (cantidad) AS cantidad,sum(d.total) AS precio_total,P.nombre,(sum(d.total)/SUM (cantidad)) as unitario FROM  "
                + " historico_detalletickets d INNER JOIN productos P ON P .id_producto = d.id_producto inner join historico_tickets t on t.id_ticket = d.id_ticket WHERE	t.id_ticket =  " + id + " and  t.id_empresa =  " + idEmpresa
                + " and d.cancelado = false group by t.id_ticket,d.id_producto,p.nombre,p.precio,t.id_folio,t.id_mesa,t.total,t.cortesia,t.efectivo_porcentaje,d.id_producto,d.cortesia,d.efectivo_porcentaje,t.nombre,t.iva ";

        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        Map<String, Object> resultadoFinal = new HashMap<String, Object>();
        resultadoFinal.put("resultado", resultado);

        query = "select * from historico_promocion_ticket where id_ticket = " + id;
        List<Map<String, Object>> promociones = jdbcTemplate.queryForList(query);
        for (Map<String, Object> item : promociones) {
            int id_promocion = Integer.parseInt(String.valueOf(item.get("id_promocion")));
            query = String.format("select * from historico_promocion_productos where id_ticket = %1$s and id_promocion = %2$s", id, id_promocion);
            List<Map<String, Object>> objProductoPromocion = jdbcTemplate.queryForList(query);
            item.put("productos", objProductoPromocion);
        }

        resultadoFinal.put("promociones", promociones);

        return resultadoFinal;
    }
    
      public Object obtenerDetalleAgrupadoticketfinalEspecial(String id, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = " SELECT	t.id_ticket,t.id_mesa,t.iva,t.nombre as nombre_ticket,t.cortesia as cortesia_ticket,t.efectivo_porcentaje as descuento_ticket, t.id_folio,t.total,d.id_producto,d.id_producto,d.cortesia,d.efectivo_porcentaje,SUM (cantidad) AS cantidad,sum(d.total) AS precio_total,P.nombre,(sum(d.total)/SUM (cantidad)) as unitario FROM  "
                + " historico_detalletickets d INNER JOIN productos P ON P .id_producto = d.id_producto inner join historico_corte_especial t on t.id_ticket = d.id_ticket WHERE	t.id_ticket =  " + id + " and  t.id_empresa =  " + idEmpresa
                + " and d.cancelado = false group by t.id_ticket,d.id_producto,p.nombre,p.precio,t.id_folio,t.id_mesa,t.total,t.cortesia,t.efectivo_porcentaje,d.id_producto,d.cortesia,d.efectivo_porcentaje,t.nombre,t.iva ";

        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);

        Map<String, Object> resultadoFinal = new HashMap<String, Object>();
        resultadoFinal.put("resultado", resultado);

        query = "select * from historico_promocion_ticket where id_ticket = " + id;
        List<Map<String, Object>> promociones = jdbcTemplate.queryForList(query);
        for (Map<String, Object> item : promociones) {
            int id_promocion = Integer.parseInt(String.valueOf(item.get("id_promocion")));
            query = String.format("select * from historico_promocion_productos where id_ticket = %1$s and id_promocion = %2$s", id, id_promocion);
            List<Map<String, Object>> objProductoPromocion = jdbcTemplate.queryForList(query);
            item.put("productos", objProductoPromocion);
        }

        resultadoFinal.put("promociones", promociones);

        return resultadoFinal;
    }

    public Object cobrarTicket(Tickets ticket, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from tickets where id_ticket = %1$s and id_empresa = %2$s";
        query = String.format(query, ticket.getIdTicket(), idEmpresa);
        Map<String, Object> resultado = new HashMap<String, Object>();

        resultado = this.jdbcTemplate.queryForMap(query);
        int id_ticket = Integer.parseInt(String.valueOf(resultado.get("id_ticket")));
        int id_user = Integer.parseInt(String.valueOf(resultado.get("id_user")));
        String fecha = String.valueOf(resultado.get("fecha"));
        int id_folio = Integer.parseInt(String.valueOf(resultado.get("id_folio")));
        int id_sucursal = Integer.parseInt(String.valueOf(resultado.get("id_sucursal")));
        String hora = String.valueOf(resultado.get("hora"));
        int id_mesa = Integer.parseInt(String.valueOf(resultado.get("id_mesa")));
        String nombre = String.valueOf(resultado.get("nombre"));
        int cortesiaTicket = Integer.parseInt(String.valueOf(resultado.get("cortesia")));
        double descontarTicket = String.valueOf(resultado.get("efectivo_porcentaje")).isEmpty() ? 0 : Double.parseDouble(String.valueOf(resultado.get("efectivo_porcentaje")));

        if (ticket.getTarjeta() != 0) {
            query = String.format("insert into historico_tickets(id_ticket,id_user,fecha,id_folio,id_sucursal,id_mesa,total,cancelado,nombre,hora,cortesia,efectivo_porcentaje,id_empresa,tarjeta,tipo_propina,propina) values(%1$s,%2$s,'%3$s',%4$s,%5$s,%6$s,%7$s,%8$s,'%9$s',((now() AT TIME ZONE 'America/Mexico_City')::TIME),%10$s,%11$s,%12$s,%13$s,'%14$s',%15$s)",
                    id_ticket, id_user, fecha, id_folio, id_sucursal, id_mesa, 0, false, nombre, cortesiaTicket, descontarTicket, idEmpresa, ticket.getTarjeta(),ticket.getTipoPropina(),ticket.getPropina());
        } else {
            query = String.format("insert into historico_tickets(id_ticket,id_user,fecha,id_folio,id_sucursal,id_mesa,total,cancelado,nombre,hora,cortesia,efectivo_porcentaje,id_empresa,tipo_propina,propina) values(%1$s,%2$s,'%3$s',%4$s,%5$s,%6$s,%7$s,%8$s,'%9$s',((now() AT TIME ZONE 'America/Mexico_City')::TIME),%10$s,%11$s,%12$s,'%13$s',%14$s)",
                    id_ticket, id_user, fecha, id_folio, id_sucursal, id_mesa, 0, false, nombre, cortesiaTicket, descontarTicket, idEmpresa,ticket.getTipoPropina(),ticket.getPropina());
        }

        this.jdbcTemplate.update(query);

        query = "select d.*,(p.precio*d.cantidad) as total from detalle_ticket d inner join productos p on p.id_producto = d.id_producto where id_ticket = " + ticket.getIdTicket();
        List<Map<String, Object>> aux = this.jdbcTemplate.queryForList(query);
        query = "";

        double ticketTotal = 0;
        for (Map<String, Object> item : aux) {
            id_ticket = Integer.parseInt(String.valueOf(item.get("id_ticket")));
            int id_producto = Integer.parseInt(String.valueOf(item.get("id_producto")));
            double cantidad = Double.parseDouble(String.valueOf(item.get("cantidad")));
            String observaciones = String.valueOf(item.get("observaciones"));
            boolean cancelado = Boolean.parseBoolean(String.valueOf(item.get("cancelado")));
            double total = cancelado ? 0 : Double.parseDouble(String.valueOf(item.get("total")));
            String cortesia = String.valueOf(item.get("cortesia"));
            String efectivo = String.valueOf(item.get("efectivo_porcentaje"));

            if (cortesia.equals("1")) {

                double efectivodbl = Double.parseDouble(efectivo);
                total = total - efectivodbl;

                query += String.format("insert into historico_detalletickets values (%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s,%7$s,%8$s);",
                        id_ticket, id_producto, cantidad, observaciones, cancelado, total, cortesia, efectivodbl);

            } else if (cortesia.equals("2")) {

                double efectivodbl = Double.parseDouble(efectivo);
                total = total - ((efectivodbl * total) / 100);

                query += String.format("insert into historico_detalletickets values (%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s,%7$s,%8$s);",
                        id_ticket, id_producto, cantidad, observaciones, cancelado, total, cortesia, efectivodbl);

            } else if (cortesia.equals("3")) {

                total = 0;
                query += String.format("insert into historico_detalletickets values (%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s,%7$s,null);",
                        id_ticket, id_producto, cantidad, observaciones, cancelado, total, cortesia);
            } else {
                query += String.format("insert into historico_detalletickets values (%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s,null,null);",
                        id_ticket, id_producto, cantidad, observaciones, cancelado, total);
            }

            ticketTotal += total;
        }

        if (cortesiaTicket == 1) {
            ticketTotal -= descontarTicket;
        } else if (cortesiaTicket == 2) {
            ticketTotal -= (ticketTotal * descontarTicket) / 100;
        } else if (cortesiaTicket == 3) {
            ticketTotal = 0;
        }

        jdbcTemplate.update(query);

        query = "delete from tickets where id_ticket = " + ticket.getIdTicket() + " and id_empresa = " + idEmpresa;
        this.jdbcTemplate.update(query);

        query = " delete from detalle_ticket where id_ticket = " + ticket.getIdTicket();
        this.jdbcTemplate.update(query);

        query = " update historico_tickets set total = ?,tipo_pago = ?,iva = ? where id_ticket = " + ticket.getIdTicket() + " and id_empresa = " + idEmpresa;
        this.jdbcTemplate.update(query, new Object[]{ticketTotal, ticket.getTipoPago(), ticket.getIva()});

        query = " update mesas set ocupada = false where id_mesa = ? and id_sucursal = ? and id_empresa = ?";
        this.jdbcTemplate.update(query, new Object[]{
            id_mesa, id_sucursal, idEmpresa
        });

        query = " update mesas set ocupada = false,mesafusionada = null where mesafusionada = ? and id_sucursal = ? and id_empresa = ? ";
        this.jdbcTemplate.update(query, new Object[]{
            id_mesa, id_sucursal, idEmpresa
        });

        //Insertando el historico de promociones
        query = "";
        List<Promociones> promociones = ticket.getPromociones();
        if (promociones != null) {

            for (Promociones item : promociones) {
                int id_promocion = item.getId();
                nombre = item.getNombre();
                int cantidad = item.getCantidad();
                double total = item.getPrecio();

                query += String.format("insert into historico_promocion_ticket values (%1$s,%2$s,'%3$s',%4$s,%5$s);", id_ticket, id_promocion, nombre, cantidad, total);
                for (Productos producto : item.getListaProductos()) {
                    int id_producto = producto.getId_producto();
                    String nombreProducto = producto.getNombre();
                    int cantidadProducto = producto.getCantidad();
                    double totalProducto = producto.getTotal();
                    query += String.format("insert into historico_promocion_productos values(%1$s,%2$s,%3$s,'%4$s',%5$s,%6$s);", id_ticket, id_promocion, id_producto, nombreProducto, cantidadProducto, totalProducto);
                }
            }

            jdbcTemplate.update(query);
        }

        return resultado;
    }

    public Object obtenerdetallecocinero(int id_sucursal, int lugar, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        /*
         lugar = 1 (cocina)
         lugar = 2 (Barra)
         lugar = 3 (Barra y Cocina)
         */
        String query = " select u.nombre, t.id_ticket,d.id_producto,d.cantidad,t.id_mesa,t.id_user,d.id,p.nombre as producto,cate.nombre as categoria ,d.observaciones,p.notificacion, "
                + " (extract('hour' from (now() AT TIME ZONE 'America/Mexico_City')-fecharegistro)*60)+extract('minute' from (now() AT TIME ZONE 'America/Mexico_City')-fecharegistro) as minutos_transcurridos   "
                + " from tickets t inner join detalle_ticket d on d.id_ticket = t.id_ticket "
                + " inner join productos p on d.id_producto = p.id_producto "
                + " inner join categoria cate on p.id_categoria = cate.id "
                + " inner join users u on u.id = t.id_user "
                + " where t.id_sucursal = ? and (p.notificacion = ? or p.notificacion = 3) and d.servido = false and t.id_empresa = ? order by minutos_transcurridos desc";
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query, new Object[]{id_sucursal, lugar, idEmpresa});
        return resultado;
    }

    public Object actualizardetallecocinero(Detalle_Tickets obj, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Map<String, Object> resultado = new HashMap<String, Object>();

        try {

            int id_ticket = obj.getId_ticket();
            int id_producto = obj.getId_producto();
            double cantidad = obj.getCantidad();
            boolean servido = obj.isServido();
            int id = obj.getId();

            String query = "update detalle_ticket set cantidad = " + cantidad + ", servido = " + servido + " where id_ticket = " + id_ticket + " and id_producto = " + id_producto + " and id =  " + id;
            query = String.format(query);

            jdbcTemplate.update(query);
            this.notificar++;
            this.notificar = (this.notificar > 10000) ? 0 : this.notificar;
            resultado.put("respuesta", "Producto servido");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al insertar productos a la orden");
        }
        return resultado;
    }

    public Object getNotificaciones() {
        Map<String, Object> respuesta = new HashMap<String, Object>();
        respuesta.put("notificar", this.notificar);
        return respuesta;
    }

    public Object actualizarDetalleTicket(Detalle_Tickets p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Map<String, Object> resultado = new HashMap<String, Object>();
        int id_pruducto = p.getId_producto();
        int id_sucursal = p.getId_sucursal();
        if (p.isCancelado()) {

            String queryInsumos = "select * from insumos where id_producto = %1$s and id_sucursal = %2$s ";
            queryInsumos = String.format(queryInsumos, id_pruducto, id_sucursal);
            List<Map<String, Object>> resultadoinsumos = jdbcTemplate.queryForList(queryInsumos);
            for (Map<String, Object> insumo : resultadoinsumos) {
                int id_inventario = Integer.parseInt(String.valueOf(insumo.get("id_inventario")));
                double cantidad = Double.parseDouble(String.valueOf(insumo.get("cantidad")));

                String queryInventario = String.format("update inventario set cantidad = cantidad + (%1$s * %2$s) where id_inventario = %3$s and id_sucursal = %4$s and id_empresa = %5$s ", cantidad, p.getCantidad(), id_inventario, id_sucursal, idEmpresa);
                jdbcTemplate.update(queryInventario);
            }
        }

        if (p.getCortesia() == 1 || p.getCortesia() == 2) {//efectivo

            String update = "update detalle_ticket set cortesia = %1$s,efectivo_porcentaje=%2$s where id = %3$s";
            update = String.format(update, p.getCortesia(), p.getEfectivo_porcentaje(), p.getId());
            try {
                jdbcTemplate.update(update);
                resultado.put("respuesta", "Descuento aplicado al producto");
            } catch (Exception e) {
                resultado.put("respuesta", "Error al actualizar el producto");

            }

        } else if (p.getCortesia() == 3) {//cortesía
            String update = "update detalle_ticket set cortesia = %1$s where id = %2$s";
            update = String.format(update, p.getCortesia(), p.getId());
            try {
                jdbcTemplate.update(update);
                resultado.put("respuesta", "Descuento aplicado al producto");
            } catch (Exception e) {
                resultado.put("respuesta", "Error al actualizar el producto");

            }
        } else {

            if (p.isCancelado()) {
                p.setCantidad(0);
            }

            String query = "update detalle_ticket set cantidad = " + p.getCantidad() + ",cancelado=" + p.isCancelado() + " where id = " + p.getId();
            query = String.format(query);
            try {
                jdbcTemplate.update(query);
                resultado.put("respuesta", "Producto actualizado");
            } catch (Exception e) {
                resultado.put("respuesta", "Error al actualizar el producto");

            }
        }

        return resultado;
    }

    public Object eliminarDetalleTicket(String id, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "delete from detalle_ticket where id = %1$s";
        query = String.format(query, id);
        Map<String, Object> resultado = new HashMap<String, Object>();
        try {
            jdbcTemplate.update(query);
            resultado.put("respuesta", "Producto eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar el producto");

        }

        return resultado;
    }

    public Object getCanceladosTickets(String id_sucursal, String fecha, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from historico_tickets where id_sucursal=" + id_sucursal + "   and fecha = '" + fecha + "' and id_empresa = %1$s order by id_ticket desc;";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object getMesasTickets(int idSucursal, int id_mesero, String nombreEmpresa) {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "";

        if (id_mesero == 0) {
            query = String.format("select ttl.id_ticket,ttl.nombre,ttl.id_mesa,uus.nombre as nombre_mesero,ttl.id_folio,ttl.preticket,substring(ttl.hora,1,5) as hora from tickets ttl inner join users uus on uus.id = ttl.id_user where  ttl.id_sucursal = %1$s and ttl.id_empresa = %2$s order by ttl.id_ticket asc", idSucursal, idEmpresa);
        } else {
            query = String.format("select ttl.id_ticket,ttl.nombre,ttl.id_mesa,uus.nombre as nombre_mesero,ttl.id_folio,ttl.preticket,substring(ttl.hora,1,5) as hora  from tickets ttl inner join users uus on uus.id = ttl.id_user where ttl.id_user = %1$s and  ttl.id_sucursal = %2$s and ttl.id_empresa = %3$s order by ttl.id_ticket asc", id_mesero, idSucursal, idEmpresa);
        }

        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object GetDesgloseCajaEspecial(int id_sucursal, String fecha, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "SELECT id_ticket,id_folio,total,tipo_pago FROM historico_tickets where id_sucursal=" + id_sucursal + "and fecha='" + fecha + "' and cancelado <> 't' and id_empresa = %1$s order by id_folio asc";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }
    
      public Object DeleteDesgloseCajaEspecial(int id_sucursal, String fecha, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "delete from historico_corte_especial where id_sucursal="+id_sucursal+ " and fecha ='"+fecha+ "' and generado =true";
        query = String.format(query, idEmpresa);
      jdbcTemplate.update(query);
        return null;
    }
    
    
    
      public Object GetDesgloseCorteEspecial(int id_sucursal, String fecha, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "SELECT * FROM historico_corte_especial where  fecha='" + fecha + "' and cancelado <> 't' and id_empresa = %1$s and generado=true order by id_folio asc";
        query = String.format(query, idEmpresa);
        List<Map<String, Object>> resultado = jdbcTemplate.queryForList(query);
        return resultado;
    }

    public Object CierraCorteEspecial(Tickets p , String nombreEmpresa) {
        
             String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Map<String, Object> resultado = new HashMap<String, Object>();

        String query = "INSERT INTO historico_corte_especial  ( SELECT * FROM historico_tickets where id_ticket not in (" + p.getListac()+ ") and id_sucursal=" + p.getId_sucursal() + " and fecha='" + p.getFechaInserta()+ "' and cancelado <> 't')";
        try {
            jdbcTemplate.update(query);

            resultado.put("respuesta", "Producto eliminado");
        } catch (Exception e) {
            resultado.put("respuesta", "Error al eliminar el producto");

            return null;

        }

        String query1 = "select min(id_folio) as minimo,count(id_folio) as numero ,min(secuencia) as mintcik from historico_corte_especial where fecha='" + p.getFechaInserta()+ "' and id_sucursal=" + p.getId_sucursal();
        Map<String, Object> min = jdbcTemplate.queryForMap(query1);
        String inicial = min.get("minimo").toString();
        String numero = min.get("numero").toString();
        String id_ticket = min.get("mintcik").toString();
        int consecutivo_folio = Integer.parseInt(id_ticket);

        for (int contador = 0; contador < Integer.parseInt(numero); contador++) {
            String query3 = "update historico_corte_especial set id_folio=" + inicial + " + " + contador + " where secuencia =" + consecutivo_folio + " and id_sucursal=" + p.getId_sucursal();
            consecutivo_folio++;
            jdbcTemplate.update(query3);
        }

        return resultado;
    }

    public Object cambiarMeseroTicket(int id_ticket, int id_mesero, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "update tickets set id_user = %1$s where id_ticket = %2$s and id_empresa = %3$s";
        query = String.format(query, id_mesero, id_ticket, idEmpresa);
        jdbcTemplate.update(query);
        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Registro actualizado con exito");
        return resultado;
    }

    public Object cambiarMesaTicket(Tickets obj, boolean unir, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select id_mesa,id_sucursal from tickets where id_ticket = %1$s and id_empresa = %2$s";
        query = String.format(query, obj.getIdTicket(), idEmpresa);
        Map<String, Object> diccionario = jdbcTemplate.queryForMap(query);
        String id_mesa = String.valueOf(diccionario.get("id_mesa"));
        String id_sucursal = String.valueOf(diccionario.get("id_sucursal"));
        query = "update mesas set ocupada = false,mesafusionada = null where id_mesa = %1$s and id_empresa = %2$s and id_sucursal = %3$s";
        query = String.format(query, id_mesa, idEmpresa, id_sucursal);
        jdbcTemplate.update(query);
        query = " update mesas set ocupada = false,mesafusionada = null where mesafusionada = %1$s and id_sucursal = %2$s and id_empresa = %3$s ";
        query = String.format(query, id_mesa, id_sucursal, idEmpresa);
        this.jdbcTemplate.update(query);
        if (!unir) {
            query = "update mesas set ocupada = true where id_mesa = %1$s and id_empresa = %2$s";
            query = String.format(query, obj.getIdMesa(), idEmpresa);
            jdbcTemplate.update(query);
            query = "update tickets set id_mesa = %1$s,nombre = '%2$s' where id_ticket = %3$s and id_empresa = %4$s ";
            query = String.format(query, obj.getIdMesa(), obj.getNombre(), obj.getIdTicket(), idEmpresa);
            jdbcTemplate.update(query);
        } else {

        }
        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Mesa cambiada correctamente");
        return resultado;
    }

    public Object aplicandoDescuentos(Tickets obj, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "update tickets set cortesia = " + obj.getCortesia();

        if (obj.getCortesia() == 1 || obj.getCortesia() == 2) {
            query += " , efectivo_porcentaje = %1$s where id_ticket = %2$s and id_empresa = %3$s";
            query = String.format(query, obj.getEfectivo_porcentaje(), obj.getIdTicket(), idEmpresa);
        } else {
            query += " , efectivo_porcentaje = 0 where id_ticket = %1$s and id_empresa = %2$s";
            query = String.format(query, obj.getIdTicket(), idEmpresa);
        }

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Porcentaje aplicado");
        jdbcTemplate.update(query);
        return resultado;
    }

    public Object mesasSeparadas(String id_ticket, int numero, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select * from tickets where id_ticket = %1$s and id_empresa = %2$s";
        query = String.format(query, id_ticket, idEmpresa);
        Map<String, Object> mapa = jdbcTemplate.queryForMap(query);

        int id_mesa = Integer.parseInt(String.valueOf(mapa.get("id_mesa")));
        String nombre = String.valueOf(mapa.get("nombre"));
        int id_user = Integer.parseInt(String.valueOf(mapa.get("id_user")));
        String fecha = String.valueOf(mapa.get("fecha"));
        int id_sucursal = Integer.parseInt(String.valueOf(mapa.get("id_sucursal")));

        query = "select COALESCE(max(id_folio),0)  as folio from historico_tickets where id_sucursal=? and id_empresa = ?";
        Map<String, Object> maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

        String maximoFolio = maximo.get("folio").toString();
        int intfolio = Integer.parseInt(maximoFolio);

        query = "select COALESCE(max(id_folio),0)  as folio from tickets where id_sucursal=? and id_empresa = ?";
        maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

        maximoFolio = maximo.get("folio").toString();

        int intfolio2 = Integer.parseInt(maximoFolio);

        intfolio = (intfolio >= intfolio2) ? intfolio : intfolio2;

        intfolio++;

        query = "update tickets set nombre = concat(nombre,'-A'), mesa_separada = true where id_ticket = %1$s and id_empresa = %2$s";
        query = String.format(query, id_ticket, idEmpresa);
        jdbcTemplate.update(query);

        if (numero == 2) {

            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-B" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";

        } else if (numero == 3) {
            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-B" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-C" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";

        } else if (numero == 4) {
            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-B" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-C" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-D" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
        } else {
            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-B" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-C" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-D" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
            query += "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,mesa_separada,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + id_mesa + ",'" + nombre + "-E" + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,true," + idEmpresa + ");";
        }

        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("respuesta", "Se ha separado las mesas");

        return resultado;
    }

    public Object insertarAlias(Tickets p, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        Object resultado = null;
        try {

            int id_user = p.getIdUser();
            int id_sucursal = p.getIdSucursal();
            int id_mesa = p.getIdMesa();
            String nombre = p.getNombre();

            String query = "select COALESCE(max(id_folio),0)  as folio from historico_tickets where id_sucursal=? and id_empresa = ?";
            Map<String, Object> maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

            String maximoFolio = maximo.get("folio").toString();
            int intfolio = Integer.parseInt(maximoFolio);

            query = "select COALESCE(max(id_folio),0)  as folio from tickets where id_sucursal=? and id_empresa = ?";
            maximo = this.jdbcTemplate.queryForMap(query, new Object[]{id_sucursal, idEmpresa});

            maximoFolio = maximo.get("folio").toString();

            int intfolio2 = Integer.parseInt(maximoFolio);

            intfolio = (intfolio >= intfolio2) ? intfolio : intfolio2;

            intfolio++;

            query = "insert into tickets (id_user,fecha,id_folio,id_sucursal,id_mesa,nombre,hora,cortesia,efectivo_porcentaje,id_empresa) values (" + id_user + ",(now() AT TIME ZONE 'America/Mexico_City')," + intfolio + "," + id_sucursal + "," + 0 + ",'" + nombre + "',((now() AT TIME ZONE 'America/Mexico_City')::TIME),0,0,%1$s) returning *;";
            query = String.format(query, idEmpresa);

            Map<String, Object> respu = jdbcTemplate.queryForMap(query);
            String id_tiket = String.valueOf(respu.get("id_ticket"));
            String nombreTicket = String.valueOf(respu.get("nombre"));

            Map<String, Object> r1 = new HashMap<String, Object>();
            r1.put("respuesta", "Ticket Ingresado Correctamente");
            r1.put("id_ticket", id_tiket);
            r1.put("id_mesa", id_mesa);
            r1.put("nombre", nombreTicket);

            resultado = r1;

        } catch (Exception e) {

            Map<String, Object> r1 = new HashMap<String, Object>();
            r1.put("respuesta", "Error al ingresar un ticket nuevo");
            resultado = r1;

        }

        return resultado;
    }

    public Object getCantidadMesas(int id_sucursal, int id_mesero, String nombreEmpresa) {

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "";

        if (id_mesero == 0) {
            query = "select count(id_ticket) as cantidad from tickets where id_empresa = %1$s and id_sucursal = %2$s ";
            query = String.format(query, idEmpresa, id_sucursal);
        } else {
            query = "select count(id_ticket) as cantidad from tickets where id_empresa = %1$s and id_sucursal = %2$s and id_user = %3$s";
            query = String.format(query, idEmpresa, id_sucursal, id_mesero);
        }

        return this.jdbcTemplate.queryForMap(query);
    }

    public Object obtenerTicketactivo(int id_ticket, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String query = "select id_ticket from tickets where id_ticket = %1$s and id_empresa = %2$s;";
        query = String.format(query, id_ticket, idEmpresa);

        Map<String, Object> resultado = jdbcTemplate.queryForMap(query);

        return resultado;

    }

    public Object cambiarMesaProducto(Detalle_Tickets detalle, int id_ticketnuevo, String nombreEmpresa) {

        String query = "update detalle_ticket set id_ticket = %1$s where id = %2$s and id_ticket = %3$s";
        query = String.format(query, id_ticketnuevo, detalle.getId_producto(), detalle.getId_ticket());
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("resultado", "Cambio de mesa exitoso");

        return resultado;

    }

    public Object unircuentas(List<Tickets> lista, int id_ticket, String nombreEmpresa) {
        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));

        String mesa = "select * from tickets where id_ticket = %1$s and id_empresa = %2$s";
        mesa = String.format(mesa, id_ticket, idEmpresa);
        Map<String, Object> res = jdbcTemplate.queryForMap(mesa);

        int id_mesa = Integer.parseInt(String.valueOf(res.get("id_mesa")));

        String query = "update tickets set nombre = concat('Cuentas unidas: Mesas ',nombre,%3$s) where (id_ticket = (%1$s)) and id_empresa = %2$s";
        String tickets = "";
        String nombres = "";

        String queryMesas = "";
        String queryDetalle = "";
        for (Tickets item : lista) {
            tickets += item.getIdTicket() + ",";
            nombres += item.getNombre() + ",";

            String aux = "update mesas set mesafusionada = %1$s where id_mesa = %2$s and id_empresa = %3$s and id_sucursal = %4$s;";
            aux = String.format(aux, id_mesa, item.getIdMesa(), idEmpresa, item.getIdSucursal());
            queryMesas += aux;

        }

        tickets = tickets.substring(0, tickets.length() - 1);
        nombres = nombres.substring(0, nombres.length() - 1);
        nombres = "'" + nombres + "'";

        query = String.format(query, id_ticket, idEmpresa, nombres.toLowerCase().replace("mesa", ""));

        jdbcTemplate.update(query);
        jdbcTemplate.update(queryMesas);

        query = "delete from tickets where id_ticket in (%1$s) and id_empresa = %2$s";
        query = String.format(query, tickets, idEmpresa);
        jdbcTemplate.update(query);

        query = "update detalle_ticket set id_ticket = %1$s where id_ticket in (%2$s);";
        query = String.format(query, id_ticket, tickets);
        jdbcTemplate.update(query);

        Map<String, Object> resultado = new HashMap<String, Object>();
        resultado.put("resultado", "Mesas unidad exitosamente");

        return resultado;
    }

    public Object separaritems(List<Detalle_Tickets> lista, int id_ticket, String nombreEmpresa) {

        Map<String, Object> resultado = new HashMap<String, Object>();

        String query = "";

        for(Detalle_Tickets detalle : lista){
         if (!detalle.isCopia()) {
            query = "update detalle_ticket set cantidad = %1$s where id_ticket = %2$s and id = %3$s ";
            query = String.format(query, detalle.getCantidad(), detalle.getId_ticket(), detalle.getId());
            jdbcTemplate.update(query);
        } else {
             
            String cortesia = detalle.getCortesia() == 0?"null":String.valueOf(detalle.getCortesia());
            String efectivoporcentaje = detalle.getEfectivo_porcentaje()== 0?"null":String.valueOf(detalle.getEfectivo_porcentaje());
             
            query = "insert into detalle_ticket (id_ticket,id_producto,cantidad,servido,observaciones,tipo_producto,fecharegistro,cancelado,cortesia,efectivo_porcentaje) "
                    + "values (%1$s,%2$s,%3$s,%4$s,'%5$s',%6$s,now(),%7$s,%8$s,%9$s)";

            query = String.format(query, detalle.getId_ticket(), detalle.getId_producto(), detalle.getCantidad(), detalle.isServido(),detalle.getObservaciones(), detalle.getTipo_producto(), false, cortesia, efectivoporcentaje);
            jdbcTemplate.update(query);
        }
        }

        resultado.put("respuesta", "Intercambio realizadp");

        return resultado;
    }
    
    public Object actualizamesa(Tickets p, String nombreEmpresa) {
    	
        Map<String, Object> resultado = new HashMap<String, Object>();

        String queryempresa = "select id from empresas where usuario = '%1$s'";
        queryempresa = String.format(queryempresa, nombreEmpresa);
        Map<String, Object> empresa = jdbcTemplate.queryForMap(queryempresa);
        int idEmpresa = Integer.parseInt(String.valueOf(empresa.get("id")));
        
        String querymesa = "update  tickets set nombre ='"+p.getNuevo()+"' where id_mesa="+ p.getIdMesa()+" and nombre = '"+p.getNombre()+"' and id_empresa="+idEmpresa+";";
        jdbcTemplate.update(querymesa);
        
    	return resultado;
    }

}
