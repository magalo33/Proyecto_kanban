package com.sophos.ws.dto.tarea;

import com.sophos.ws.domain.Tarea;
import java.util.List;
import lombok.Data;

@Data
public class TareaResponseDto {
    int error;
    String descripcion;
    List<Tarea> listaTareas;    
}
