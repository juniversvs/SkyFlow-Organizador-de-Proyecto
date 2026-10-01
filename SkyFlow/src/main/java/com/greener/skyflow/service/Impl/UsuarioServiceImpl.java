package com.greener.skyflow.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greener.skyflow.entity.Usuario;
import com.greener.skyflow.repository.UsuarioRepositorio;
import com.greener.skyflow.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public List<Usuario> listarTodosUsuario() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepositorio.findById(id);
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public Usuario buscarByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }

    @Override
    public Usuario buscarByUsernameAndClave(String username, String clave) {
        return usuarioRepositorio.findByUsernameAndClave(username, clave);
    }
}