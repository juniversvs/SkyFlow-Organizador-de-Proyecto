package com.greener.skyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greener.skyflow.entity.ProyectoUsuario;

@Repository
public interface ProyectoUsuarioRepositorio extends JpaRepository<ProyectoUsuario, Integer> {

    // Buscar los usuarios pertenecientes a un proyecto
    List<ProyectoUsuario> findByIdProyecto(Integer idProyecto);

    // Buscar los proyectos donde participa un usuario
    List<ProyectoUsuario> findByIdUsuario(Integer idUsuario);
    
    List<ProyectoUsuario> findByIdProyectoAndRol(
    	    Integer idProyecto,
    	    String rol
    	);
}
