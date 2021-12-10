/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.ws.service;

import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import java.util.List;

/**
 *
 * @author Magalo
 */
public interface IComentariosportareaService {
    Comentariosportarea registrarComentario(Comentariosportarea comentario);
    //List<Comentariosportarea> ComentariosPortarea(Tarea tarea);    
    Comentariosportarea editarComentario(Comentariosportarea comentario);
    void eliminarComentario(Comentariosportarea comentario);
    void eliminarComentarioPorTarea(Long idtarea);
    
    
}
