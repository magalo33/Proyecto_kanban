/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sophos.ws.dto.tarea;

import lombok.Data;

/**
 *
 * @author Magalo
 */
@Data
public class TareaBucket {
    TareaBaseDto tareaBaseDto;
    String encriptedData;
}
