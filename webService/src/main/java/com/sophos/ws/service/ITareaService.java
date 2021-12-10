package com.sophos.ws.service;

import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ITareaService {
    Tarea registrarTarea(Tarea tarea);
    List<Tarea> tareasPorId(Long idtarea);
    void eliminarTarea(Tarea tarea);
    void editarTarea(Tarea tarea);
    List<Tarea> listaTareas(Long idusuario);
    List<Tarea> listaTareasPorDescripcion(String descripcion);
    List<Tarea> tareaPorDescripcionYusuario(String descripcion,Long idusuario);
}
