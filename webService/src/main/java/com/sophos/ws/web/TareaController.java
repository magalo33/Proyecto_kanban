package com.sophos.ws.web;

import com.google.gson.Gson;
import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.utils.Utils;
import com.sophos.ws.dto.tarea.TareaBaseDto;
import com.sophos.ws.dto.tarea.TareaRequestDto;
import com.sophos.ws.dto.tarea.TareaResponseDto;
import com.sophos.ws.dto.tarea.VerificacionTareaResponseDto;
import com.sophos.ws.impl.ComentariosportareaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sophos.ws.impl.TareaImpl;
import com.sophos.ws.impl.UsuarioImpl;
import java.util.List;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping(value = "/api/kanban")
@RestController
public class TareaController {
    
    @Autowired
    private TareaImpl iTareaImpl;
    
     @Autowired
    private UsuarioImpl usuarioImpl;

    
    @Autowired
    private ComentariosportareaImpl comentariosportareaImpl;    
    
    @PostMapping(value = "/tarea")
    public @ResponseBody
    TareaResponseDto registrarTarea(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody TareaRequestDto tareaRequestDto) {
        int error = 0;
        String descripcion = "Registro de tarea ok";
        Tarea tarea = null;
        Gson gson = new Gson();
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        try {
            TareaBaseDto tareaBaseDto = gson.fromJson(Utils.decryptText((headers.get("x-session-token")).get(0)),TareaBaseDto.class);
            VerificacionTareaResponseDto verificaciontareas = Utils.validarParametrosRegistroTarea(tareaBaseDto,tareaRequestDto);
            if(verificaciontareas.getError()>0){
                error = 1;
                descripcion = verificaciontareas.getDescripcion();
            }else{
                boolean validarLogin = true;
                
                List<Usuario> listaUsuarios = usuarioImpl.findByUsuarioAndPassword(tareaRequestDto.getTarea().getUsuario().getUsuario(), tareaRequestDto.getTarea().getUsuario().getPassword());
                if (listaUsuarios.size() == 0) {
                    validarLogin = false;
                } 
                if(!validarLogin){
                    error = 1;
                    descripcion = "No se pudo validar el login. Verifique sus credenciales";
                }else{
                    if(iTareaImpl.tareaPorDescripcionYusuario(tareaRequestDto.getTarea().getDescripcion(),
                            tareaRequestDto.getTarea().getUsuario().getIdusuario()).size()>0){
                        error = 1;
                        descripcion = "Esta tarea ya esta asociada a este usuario";
                    }else{
                        tarea = iTareaImpl.registrarTarea(tareaRequestDto.getTarea());
                        List<Comentariosportarea> comentariosportareasListBody = tareaRequestDto.getTarea().getComentariosportareasList();
                        for (int i = 0; i < comentariosportareasListBody.size(); i++) {
                            Comentariosportarea cptBody=comentariosportareasListBody.get(i);
                            cptBody.setIdtarea(tarea);
                            comentariosportareaImpl.registrarComentario(cptBody);
                        }                                                
                    }
                }                    
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = "Se presentó un error al tratar de registrar la tarea";
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setTarea(tarea);
        return tareaResponseDto;
    }
    
    
    /*
    @Override
    public Tarea registrarTarea(Tarea tarea) {
        return itareasDao.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        itareasDao.delete(tarea);
    }

    @Override
    public List<Tarea> listaTareas(Usuario usuario) {
        return itareasDao.findByIdusuario(usuario);
    }

    @Override
    public Tarea tareaPorId(Long idtarea) {
        return itareasDao.findById(idtarea).orElse(null);
    }
    */
    
    
}
