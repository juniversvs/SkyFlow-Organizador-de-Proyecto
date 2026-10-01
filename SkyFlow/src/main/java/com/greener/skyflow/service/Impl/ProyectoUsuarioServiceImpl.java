package com.greener.skyflow.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greener.skyflow.entity.ProyectoUsuario;
import com.greener.skyflow.repository.ProyectoUsuarioRepositorio;
import com.greener.skyflow.service.ProyectoUsuarioService;

@Service
public class ProyectoUsuarioServiceImpl implements ProyectoUsuarioService {

    @Autowired
    private ProyectoUsuarioRepositorio proyectoUsuarioRepositorio;

    @Override
    public ProyectoUsuario guardarProyectoUsuario(ProyectoUsuario proyectoUsuario) {
        return proyectoUsuarioRepositorio.save(proyectoUsuario);
    }

    @Override
    public List<ProyectoUsuario> listarTodosProyectoUsuario() {
        return proyectoUsuarioRepositorio.findAll();
    }

    @Override
    public Optional<ProyectoUsuario> buscarPorId(Integer id) {
        return proyectoUsuarioRepositorio.findById(id);
    }

    @Override
    public ProyectoUsuario actualizarProyectoUsuario(ProyectoUsuario proyectoUsuario) {
        return proyectoUsuarioRepositorio.save(proyectoUsuario);
    }

    @Override
    public void eliminarProyectoUsuario(Integer id) {
        proyectoUsuarioRepositorio.deleteById(id);
    }

    @Override
    public List<ProyectoUsuario> buscarPorIdProyecto(Integer idProyecto) {
        return proyectoUsuarioRepositorio.findByIdProyecto(idProyecto);
    }

    @Override
    public List<ProyectoUsuario> buscarPorIdUsuario(Integer idUsuario) {
        return proyectoUsuarioRepositorio.findByIdUsuario(idUsuario);
    }

    @Override
    public List<ProyectoUsuario> buscarPorIdProyectoAndRol(
            Integer idProyecto, String rol) {

        return proyectoUsuarioRepositorio.findByIdProyectoAndRol(idProyecto, rol);
    }
}