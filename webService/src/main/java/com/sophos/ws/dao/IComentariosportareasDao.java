package com.sophos.ws.dao;

import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IComentariosportareasDao extends JpaRepository<Comentariosportarea,Long>{

    @Query("SELECT c FROM Comentariosportarea c WHERE c.idtarea=:tarea")
    List<Comentariosportarea> ComentariosPortarea(@Param("tarea") Tarea tarea);    
    
}
