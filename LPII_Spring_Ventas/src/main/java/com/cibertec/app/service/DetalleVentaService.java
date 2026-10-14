package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.entity.DetalleVenta;

public interface DetalleVentaService {
	
	DetalleVenta guardarDetalleVenta(DetalleVenta detalle);
	
	List<DetalleVenta> listarTodosDetalleVenta();
	
	List<DetalleVenta> buscarDetalleVentaByNroVentas(Long nroVenta);

}
