package com.greener.skyflow.service;

import java.util.List;
import java.util.Optional;

import com.greener.skyflow.entity.Usuario;

public interface UsuarioService {

    public Usuario guardarUsuario(Usuario usuario);

    public List<Usuario> listarTodosUsuario();

    public Optional<Usuario> buscarPorId(Integer id);

    public Usuario actualizarUsuario(Usuario usuario);

    public void eliminarUsuario(Integer id);

    public Usuario buscarByUsername(String username);

    public Usuario buscarByUsernameAndClave(String username, String clave);

}