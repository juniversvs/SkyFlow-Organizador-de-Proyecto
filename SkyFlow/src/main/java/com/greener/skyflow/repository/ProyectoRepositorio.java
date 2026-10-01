package com.greener.skyflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greener.skyflow.entity.Proyecto;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, Integer> {

    List<Proyecto> findByIdCreador(Integer idCreador);

}