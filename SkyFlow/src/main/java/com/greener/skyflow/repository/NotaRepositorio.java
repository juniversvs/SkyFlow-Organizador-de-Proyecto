package com.greener.skyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greener.skyflow.entity.Nota;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, Integer> {

    // Buscar todas las notas de un proyecto
    List<Nota> findByIdProyecto(Integer idProyecto);

    // Buscar notas de un proyecto según su importancia
    List<Nota> findByIdProyectoAndImportancia(
        Integer idProyecto,
        String importancia
    );

    // Buscar notas según su importancia
    List<Nota> findByImportancia(String importancia);

}
