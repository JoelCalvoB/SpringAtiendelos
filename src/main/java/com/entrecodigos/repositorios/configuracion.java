package com.entrecodigos.repositorios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class configuracion {

	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private Long id_empresa;
	private Long id_sucursal;
	private String nombreEmpresa;
	private boolean esImpresoraWifi;
	private boolean esImpresoraBluetooth;
	private String ipcajero;
	private String ipCocinaCaliente;
	private String ipCocinaFria;
	private String ipBarra;
	private String strBluetoothCajero;
	private String strBluetoothCocina;
	private String strBluetoothCocinaFria;
	private String strBluetoothBarra;
	private boolean disconected;
	private boolean masImpresoras;
	private boolean impresora80;
	private boolean impresoraCajero;
	private boolean impresoraCocina;
	private boolean impresoraCocinaFria;
	private boolean impresoraBarra;
	private boolean enviarCocina;
	private boolean enviarBarra;
	private boolean iva;
	private boolean ivarecupera;
	private boolean varias;
	private boolean semionline;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId_empresa() {
		return id_empresa;
	}
	public void setId_empresa(Long id_empresa) {
		this.id_empresa = id_empresa;
	}
	public Long getId_sucursal() {
		return id_sucursal;
	}
	public void setId_sucursal(Long id_sucursal) {
		this.id_sucursal = id_sucursal;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public boolean isEsImpresoraWifi() {
		return esImpresoraWifi;
	}
	public void setEsImpresoraWifi(boolean esImpresoraWifi) {
		this.esImpresoraWifi = esImpresoraWifi;
	}
	public boolean isEsImpresoraBluetooth() {
		return esImpresoraBluetooth;
	}
	public void setEsImpresoraBluetooth(boolean esImpresoraBluetooth) {
		this.esImpresoraBluetooth = esImpresoraBluetooth;
	}
	public String getIpcajero() {
		return ipcajero;
	}
	public void setIpcajero(String ipcajero) {
		this.ipcajero = ipcajero;
	}
	public String getIpCocinaCaliente() {
		return ipCocinaCaliente;
	}
	public void setIpCocinaCaliente(String ipCocinaCaliente) {
		this.ipCocinaCaliente = ipCocinaCaliente;
	}
	public String getIpCocinaFria() {
		return ipCocinaFria;
	}
	public void setIpCocinaFria(String ipCocinaFria) {
		this.ipCocinaFria = ipCocinaFria;
	}
	public String getIpBarra() {
		return ipBarra;
	}
	public void setIpBarra(String ipBarra) {
		this.ipBarra = ipBarra;
	}
	public String getStrBluetoothCajero() {
		return strBluetoothCajero;
	}
	public void setStrBluetoothCajero(String strBluetoothCajero) {
		this.strBluetoothCajero = strBluetoothCajero;
	}
	public String getStrBluetoothCocina() {
		return strBluetoothCocina;
	}
	public void setStrBluetoothCocina(String strBluetoothCocina) {
		this.strBluetoothCocina = strBluetoothCocina;
	}
	public String getStrBluetoothCocinaFria() {
		return strBluetoothCocinaFria;
	}
	public void setStrBluetoothCocinaFria(String strBluetoothCocinaFria) {
		this.strBluetoothCocinaFria = strBluetoothCocinaFria;
	}
	public String getStrBluetoothBarra() {
		return strBluetoothBarra;
	}
	public void setStrBluetoothBarra(String strBluetoothBarra) {
		this.strBluetoothBarra = strBluetoothBarra;
	}
	public boolean isDisconected() {
		return disconected;
	}
	public void setDisconected(boolean disconected) {
		this.disconected = disconected;
	}
	public boolean isMasImpresoras() {
		return masImpresoras;
	}
	public void setMasImpresoras(boolean masImpresoras) {
		this.masImpresoras = masImpresoras;
	}
	public boolean isImpresora80() {
		return impresora80;
	}
	public void setImpresora80(boolean impresora80) {
		this.impresora80 = impresora80;
	}
	public boolean isImpresoraCajero() {
		return impresoraCajero;
	}
	public void setImpresoraCajero(boolean impresoraCajero) {
		this.impresoraCajero = impresoraCajero;
	}
	public boolean isImpresoraCocina() {
		return impresoraCocina;
	}
	public void setImpresoraCocina(boolean impresoraCocina) {
		this.impresoraCocina = impresoraCocina;
	}
	public boolean isImpresoraCocinaFria() {
		return impresoraCocinaFria;
	}
	public void setImpresoraCocinaFria(boolean impresoraCocinaFria) {
		this.impresoraCocinaFria = impresoraCocinaFria;
	}
	public boolean isImpresoraBarra() {
		return impresoraBarra;
	}
	public void setImpresoraBarra(boolean impresoraBarra) {
		this.impresoraBarra = impresoraBarra;
	}
	public boolean isEnviarCocina() {
		return enviarCocina;
	}
	public void setEnviarCocina(boolean enviarCocina) {
		this.enviarCocina = enviarCocina;
	}
	public boolean isEnviarBarra() {
		return enviarBarra;
	}
	public void setEnviarBarra(boolean enviarBarra) {
		this.enviarBarra = enviarBarra;
	}
	public boolean isIva() {
		return iva;
	}
	public void setIva(boolean iva) {
		this.iva = iva;
	}
	public boolean isIvarecupera() {
		return ivarecupera;
	}
	public void setIvarecupera(boolean ivarecupera) {
		this.ivarecupera = ivarecupera;
	}
	public boolean isVarias() {
		return varias;
	}
	public void setVarias(boolean varias) {
		this.varias = varias;
	}
	public boolean isSemionline() {
		return semionline;
	}
	public void setSemionline(boolean semionline) {
		this.semionline = semionline;
	}
	
	
	
	
	
	
	
}
