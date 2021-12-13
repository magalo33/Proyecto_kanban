package com.sophos.ws.web;

import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Estado;
import com.sophos.ws.impl.EstadoImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/kanban")
@RestController
@CrossOrigin(origins="*",methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class EstadoController {
    
    @Autowired
    private EstadoImpl estadoImpl;
    
    
    @GetMapping(value = "/estado")
    public @ResponseBody
    List<Estado> listaEstado() {
        WsApplication.registrarInfoLog("Llamado a la lista de estados");
        return estadoImpl.listaEstados();
    }
    
    @GetMapping(value = "/estado/{idestado}")
    public @ResponseBody
    Estado estadoPorId(@PathVariable("idestado") long idestado) {
        WsApplication.registrarInfoLog("Buscar estado por id ".concat(String.valueOf(idestado)));
        return estadoImpl.estadoPorId(idestado);
    }
    
}
