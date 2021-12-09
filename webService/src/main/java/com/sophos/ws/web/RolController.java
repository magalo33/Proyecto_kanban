package com.sophos.ws.web;

import com.sophos.ws.WsApplication;
import com.sophos.ws.impl.RolImpl;
import com.sophos.ws.domain.Rol;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/kanban")
@RestController
public class RolController {
    
    @Autowired
    private RolImpl rolImpl;

    @GetMapping(value = "/rol")
    public @ResponseBody
    List<Rol> listaUsuarios() {
        WsApplication.registrarInfoLog("Llamado a la lista de roles");
        return rolImpl.listaRoles();
    }
    
    
    @GetMapping(value = "/rol/{idrol}")
    public @ResponseBody
    Rol rolPorId(@PathVariable("idrol") long idrol) {
        WsApplication.registrarInfoLog("Buscar rol por id ".concat(String.valueOf(idrol)));
        return rolImpl.rolPorId(idrol);
    }

}
