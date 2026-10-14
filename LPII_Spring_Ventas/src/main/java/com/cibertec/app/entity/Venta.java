package com.cibertec.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "ventas")
public class Venta implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "nroventa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date fecha;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "idclie", nullable = false)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "codasesor", nullable = false)
	private AsesorVenta asesorVenta;

	@Column(precision = 10, scale = 2)
	private BigDecimal subtotal= BigDecimal.ZERO;

	@Column(precision = 10, scale = 2)
	private BigDecimal igv= BigDecimal.ZERO;

	@Column(precision = 10, scale = 2)
	private BigDecimal total= BigDecimal.ZERO;

	@Column(precision = 10, scale = 2)
	private BigDecimal ganancia= BigDecimal.ZERO;
	
	
	@Transient
	private Integer idAsesor;
	
	@Transient 
	private Integer idCliente;


}
