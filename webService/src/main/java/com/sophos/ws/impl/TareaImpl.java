package com.sophos.ws.impl;

import com.sophos.ws.dao.ITareasDao;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.service.ITareaService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaImpl implements ITareaService{
    
    @Autowired
    private ITareasDao itareasDao;

    @Override
    public Tarea registrarTarea(Tarea tarea) {
        return itareasDao.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        itareasDao.delete(tarea);
    }

    @Override
    public List<Tarea> listaTareas(Long idusuario) {
        return itareasDao.findByIdusuario(idusuario);
    }

    @Override
    public List<Tarea> tareasPorId(Long idtarea) {
        List<Long> listaId = new ArrayList<>();
        listaId.add(idtarea);
        return itareasDao.findAllById(listaId);
    }

    @Override
    public List<Tarea> listaTareasPorDescripcion(String descripcion) {
        return itareasDao.findByDescripcionIgnoreCaseContaining(descripcion);
    }

    @Override
    public List<Tarea> tareaPorDescripcionYusuario(String descripcion, Long idusuario) {
        return itareasDao.tareaPorDescripcionYusuario(descripcion, idusuario);
    }

    @Override
    public void editarTarea(Tarea tarea) {
        itareasDao.save(tarea);
    }
    
}
