package com.greener.skyflow.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greener.skyflow.entity.Nota;
import com.greener.skyflow.repository.NotaRepositorio;
import com.greener.skyflow.service.NotaService;

@Service
public class NotaServiceImpl implements NotaService {

    @Autowired
    private NotaRepositorio notaRepositorio;

    @Override
    public Nota guardarNota(Nota nota) {
        return notaRepositorio.save(nota);
    }

    @Override
    public List<Nota> listarTodasNotas() {
        return notaRepositorio.findAll();
    }

    @Override
    public Optional<Nota> buscarPorId(Integer id) {
        return notaRepositorio.findById(id);
    }

    @Override
    public Nota actualizarNota(Nota nota) {
        return notaRepositorio.save(nota);
    }

    @Override
    public void eliminarNota(Integer id) {
        notaRepositorio.deleteById(id);
    }

    @Override
    public List<Nota> buscarPorIdProyecto(Integer idProyecto) {
        return notaRepositorio.findByIdProyecto(idProyecto);
    }

    @Override
    public List<Nota> buscarPorIdProyectoAndImportancia(
            Integer idProyecto, String importancia) {

        return notaRepositorio.findByIdProyectoAndImportancia(
                idProyecto, importancia);
    }

    @Override
    public List<Nota> buscarPorImportancia(String importancia) {
        return notaRepositorio.findByImportancia(importancia);
    }
}