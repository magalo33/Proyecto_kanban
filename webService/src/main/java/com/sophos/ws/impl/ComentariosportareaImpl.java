package com.sophos.ws.impl;

import com.sophos.ws.dao.IComentariosportareasDao;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.service.IComentariosportareaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComentariosportareaImpl implements IComentariosportareaService{
    
    @Autowired
    private IComentariosportareasDao iComentariosportareasDao;

    @Override
    public Comentariosportarea registrarComentario(Comentariosportarea comentario) {
        return iComentariosportareasDao.save(comentario);
    }

    /*@Override
    public List<Comentariosportarea> ComentariosPortarea(Tarea tarea) {
        return iComentariosportareasDao.ComentariosPortarea(tarea);
    }*/

    @Override
    public Comentariosportarea editarComentario(Comentariosportarea comentario) {
        return iComentariosportareasDao.save(comentario);
    }

    @Override
    public void eliminarComentario(Comentariosportarea comentario) {
        iComentariosportareasDao.delete(comentario);
    }

    @Override
    public void eliminarComentarioPorTarea(Long idtarea) {
        iComentariosportareasDao.eliminarComentariosPorTarea(idtarea);
    }
    
}
