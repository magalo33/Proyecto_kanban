package com.sophos.ws.dao;

import com.sophos.ws.domain.Tarea;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITareasDao extends JpaRepository<Tarea,Long>{
    
    @Query("SELECT t FROM Tarea t WHERE t.descripcion=:descripcion AND t.idusuario=:idusuario")
    List<Tarea> tareaPorDescripcionYusuario(
            @Param("descripcion") String descripcion,
            @Param("idusuario")Long idusuario);
        
     List<Tarea> findByDescripcionIgnoreCaseContaining(String descripcion);
     List<Tarea> findByIdusuario(Long idusuario);
     
    @Transactional
    @Modifying
    @Query("DELETE FROM Tarea t WHERE t.idtarea=:idtarea")
    void eliminarTareaPorId(@Param("idtarea") Long idtarea);   
}
