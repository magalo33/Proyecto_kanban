package com.sophos.ws.dto.tarea;

import lombok.Data;

@Data
public class TareaBucket {
    TareaBaseDto tareaBaseDto;
    String encriptedData;
}
