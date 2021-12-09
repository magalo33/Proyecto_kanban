package com.sophos.ws.impl;

import com.sophos.ws.dao.IUsuariosDao;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.service.IUsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioImpl implements IUsuarioService{

    @Autowired
    private IUsuariosDao iUsuarioDao;
    
    @Override
    public List<Usuario> listaUsuarios() {
        return iUsuarioDao.findAll();
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        return iUsuarioDao.save(usuario);
    }

    @Override
    public Usuario usuarioPorId(Long idusuario) {
        return this.iUsuarioDao.findById(idusuario).orElse(null);
    }

    @Override
    public List<Usuario> listarPorusuario(String usuario) {
        return this.iUsuarioDao.findByUsuarioIgnoreCaseContaining(usuario);
    }

    @Override
    public List<Usuario> findByUsuarioAndPassword(String usuario, String password) {
        return this.iUsuarioDao.findByUsuarioAndPassword(usuario, password);
    }
    
}
