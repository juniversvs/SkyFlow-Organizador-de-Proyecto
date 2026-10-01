package com.greener.skyflow.service;

import java.util.List;
import java.util.Optional;

import com.greener.skyflow.entity.Proyecto;

public interface ProyectoService {

    public Proyecto guardarProyecto(Proyecto proyecto);

    public List<Proyecto> listarTodosProyecto();

    public Optional<Proyecto> buscarPorId(Integer id);

    public Proyecto actualizarProyecto(Proyecto proyecto);

    public void eliminarProyecto(Integer id);

    public List<Proyecto> buscarPorIdCreador(Integer idCreador);

}