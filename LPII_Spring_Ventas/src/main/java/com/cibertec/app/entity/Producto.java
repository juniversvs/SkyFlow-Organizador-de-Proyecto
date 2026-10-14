package com.cibertec.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idproducto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProd;

	private String codigo;
	private String descripcion;
		
	@Column(name = "precio_compra")
	private BigDecimal precioCompra;
	
	@Column(name = "precio_venta")
	private BigDecimal precioVenta;

	private int stock;

	@ManyToOne
	@JoinColumn(name= "idcate")
	private Categoria categoria;
	
	public Producto(String codigo) {
		this.codigo = codigo;
	}
	
	//Nuevo constructor
	public Producto(Integer idProd, String codigo, String descripcion,
	        BigDecimal precioCompra, BigDecimal precioVenta, int stock) {
	    this.idProd = idProd;
	    this.codigo = codigo;
	    this.descripcion = descripcion;
	    this.precioCompra = precioCompra;
	    this.precioVenta = precioVenta;
	    this.stock = stock;
	}

	    
	public void restarExistencia(int cantidad) {
	    this.stock -= cantidad;
	}
	    
	public boolean sinExistencia() {
	    return this.stock <= 0;
	}
}