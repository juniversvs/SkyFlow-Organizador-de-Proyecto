package com.cibertec.app.service;

import java.util.List;

import com.cibertec.app.entity.AsesorVenta;

public interface AsesorVentaService {

	List<AsesorVenta> listarTodosAsesores();
	
	AsesorVenta buscarAsesorVentaById(Integer id);
}
