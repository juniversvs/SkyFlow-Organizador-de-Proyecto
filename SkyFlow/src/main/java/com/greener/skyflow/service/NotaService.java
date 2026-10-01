package com.greener.skyflow.service;

import java.util.List;
import java.util.Optional;

import com.greener.skyflow.entity.Nota;

public interface NotaService {

    public Nota guardarNota(Nota nota);

    public List<Nota> listarTodasNotas();

    public Optional<Nota> buscarPorId(Integer id);

    public Nota actualizarNota(Nota nota);

    public void eliminarNota(Integer id);

    public List<Nota> buscarPorIdProyecto(Integer idProyecto);

    public List<Nota> buscarPorIdProyectoAndImportancia(
            Integer idProyecto, String importancia);

    public List<Nota> buscarPorImportancia(String importancia);

}