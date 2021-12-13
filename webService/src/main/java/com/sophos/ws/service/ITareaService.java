package com.sophos.ws.service;

import com.sophos.ws.domain.Tarea;
import java.util.List;

public interface ITareaService {
    Tarea registrarTarea(Tarea tarea);
    List<Tarea> tareasPorId(Long idtarea);
    void eliminarTarea(Tarea tarea);
    void eliminarTareaPorId(Long idtarea);
    void editarTarea(Tarea tarea);
    List<Tarea> listaTareas(Long idusuario);
    List<Tarea> listaTareasPorDescripcion(String descripcion);
    List<Tarea> tareaPorDescripcionYusuario(String descripcion,Long idusuario);
}
