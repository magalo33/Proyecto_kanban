package com.sophos.ws.service;

import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ITareaService {
    Tarea registrarTarea(Tarea tarea);
    Tarea tareaPorId(Long idtarea);
    void eliminarTarea(Tarea tarea);
    List<Tarea> listaTareas(Usuario usuario);
    List<Tarea> listaTareasPorDescripcion(String descripcion);
    List<Tarea> tareaPorDescripcionYusuario(String descripcion,Long idusuario);
}
