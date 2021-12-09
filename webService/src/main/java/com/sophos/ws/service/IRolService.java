package com.sophos.ws.service;

import com.sophos.ws.domain.Rol;
import java.util.List;

public interface IRolService {
    public List<Rol> listaRoles();
    public Rol rolPorId(Long idrol);
}
