package com.sophos.ws.impl;

import com.sophos.ws.dao.IRolesDao;
import com.sophos.ws.domain.Rol;
import com.sophos.ws.service.IRolService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolImpl implements IRolService{

    @Autowired
    private IRolesDao iRolesDao;
    
    @Override
    public List<Rol> listaRoles() {
        return iRolesDao.findAll();
    }

    @Override
    public Rol rolPorId(Long idrol) {
        return iRolesDao.findById(idrol).orElse(null);
    }
    
}
