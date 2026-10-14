package com.cibertec.app.entity;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductoParaVender extends Producto{

	private static final long serialVersionUID = 1L;
	
	private int cantidad;
	
	//Agregar constructor en entidad producto
	public ProductoParaVender(Integer idProd, String codigo, String descripcion,
			BigDecimal precioCompra , BigDecimal precioVenta, int stock , int cantidad) {
		super(idProd , codigo , descripcion , precioCompra , precioVenta , stock);
		this.cantidad= cantidad;
	}
	
	public void aumentarCantidad() {
		this.cantidad++;
	}
	
	public BigDecimal getTotal() {
		return this.getPrecioVenta().multiply(BigDecimal.valueOf(this.cantidad));
	}
	
}
