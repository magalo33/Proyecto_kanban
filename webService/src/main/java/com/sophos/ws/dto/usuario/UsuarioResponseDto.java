package com.sophos.ws.dto.usuario;

import com.sophos.ws.domain.Usuario;
import lombok.Data;

@Data
public class UsuarioResponseDto {
    int error;
    String descripcion;
    Usuario usuario;    
}
