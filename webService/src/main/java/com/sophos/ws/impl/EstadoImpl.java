package com.sophos.ws.impl;

import com.sophos.ws.dao.IEstadosDao;
import com.sophos.ws.domain.Estado;
import com.sophos.ws.service.IEstadoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoImpl implements IEstadoService{
    
    @Autowired
    private IEstadosDao iEstadoDao;

    @Override
    public List<Estado> listaEstados() {
        return iEstadoDao.findAll();
    }

    @Override
    public Estado estadoPorId(Long idestado) {
        return iEstadoDao.findById(idestado).orElse(null);
    }
    
}
