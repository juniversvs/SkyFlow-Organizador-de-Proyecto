package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.entity.ProductoParaVender;
import com.cibertec.app.entity.Venta;

public interface VentaService {

	Venta guardarVenta(Venta venta);
	
	List<Venta> ListarTodosVentas();
	
	Venta registrarVenta(Venta venta, List<ProductoParaVender> carrito);
}
