package com.greener.skyflow.service;

import java.util.List;
import java.util.Optional;

import com.greener.skyflow.entity.ProyectoUsuario;

public interface ProyectoUsuarioService {

    public ProyectoUsuario guardarProyectoUsuario(ProyectoUsuario proyectoUsuario);

    public List<ProyectoUsuario> listarTodosProyectoUsuario();

    public Optional<ProyectoUsuario> buscarPorId(Integer id);

    public ProyectoUsuario actualizarProyectoUsuario(ProyectoUsuario proyectoUsuario);

    public void eliminarProyectoUsuario(Integer id);

    public List<ProyectoUsuario> buscarPorIdProyecto(Integer idProyecto);

    public List<ProyectoUsuario> buscarPorIdUsuario(Integer idUsuario);

    public List<ProyectoUsuario> buscarPorIdProyectoAndRol(
            Integer idProyecto, String rol);

}