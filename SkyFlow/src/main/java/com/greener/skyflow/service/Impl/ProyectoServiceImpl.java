package com.greener.skyflow.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greener.skyflow.entity.Proyecto;
import com.greener.skyflow.repository.ProyectoRepositorio;
import com.greener.skyflow.service.ProyectoService;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Override
    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepositorio.save(proyecto);
    }

    @Override
    public List<Proyecto> listarTodosProyecto() {
        return proyectoRepositorio.findAll();
    }

    @Override
    public Optional<Proyecto> buscarPorId(Integer id) {
        return proyectoRepositorio.findById(id);
    }

    @Override
    public Proyecto actualizarProyecto(Proyecto proyecto) {
        return proyectoRepositorio.save(proyecto);
    }

    @Override
    public void eliminarProyecto(Integer id) {
        proyectoRepositorio.deleteById(id);
    }

    @Override
    public List<Proyecto> buscarPorIdCreador(Integer idCreador) {
        return proyectoRepositorio.findByIdCreador(idCreador);
    }
}
