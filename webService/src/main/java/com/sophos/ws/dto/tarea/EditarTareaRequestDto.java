package com.sophos.ws.dto.tarea;

import com.sophos.ws.domain.Usuario;
import com.sophos.ws.domain.Tarea;
import lombok.Data;

@Data
public class EditarTareaRequestDto {
    Usuario usuario;
    Tarea tarea;
}
