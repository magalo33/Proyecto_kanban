package com.sophos.ws.dto.tarea;

import lombok.Data;

@Data
public class TareaEdicionBucket {
    TareaEditarBaseDto tareaBaseDto;
    String encriptedData;
}
