package com.sophos.ws.service;

import com.sophos.ws.domain.Usuario;
import java.util.List;

public interface IUsuarioService {
    List<Usuario> listaUsuarios();
    Usuario registrarUsuario(Usuario usuario);
    Usuario usuarioPorId(Long idusuario);
    List<Usuario> listarPorusuario(String usuario);
    List<Usuario> findByUsuarioAndPassword(String usuario, String password);
}
