package com.sophos.ws.web;

import com.google.gson.Gson;
import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.utils.Utils;
import com.sophos.ws.dto.tarea.TareaBaseDto;
import com.sophos.ws.dto.tarea.TareaBucket;
import com.sophos.ws.dto.tarea.TareaRequestDto;
import com.sophos.ws.dto.tarea.TareaResponseDto;
import com.sophos.ws.dto.tarea.VerificacionTareaResponseDto;
import com.sophos.ws.impl.ComentariosportareaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sophos.ws.impl.TareaImpl;
import com.sophos.ws.impl.UsuarioImpl;
import com.sophos.ws.utils.AESEncryption;
import com.sophos.ws.utils.ConfiguracionUtilValues;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println("poai 1");
        int error = 0;
        String descripcion = "Registro de tarea ok";
        Tarea tarea = null;
        Gson gson = new Gson();
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        System.out.println("poai 2");
        try {
            TareaBaseDto tareaBaseDto = gson.fromJson(Utils.decryptText((headers.get("x-session-token")).get(0)),TareaBaseDto.class);
            VerificacionTareaResponseDto verificaciontareas = Utils.validarParametrosRegistroTarea(tareaBaseDto,tareaRequestDto);
            if(verificaciontareas.getError()>0){
                error = 1;
                descripcion = verificaciontareas.getDescripcion();
            }else{
                System.out.println("poai 3");
                boolean validarLogin = true;
                
                List<Usuario> listaUsuarios = usuarioImpl.findByUsuarioAndPassword(tareaRequestDto.getTarea().getUsuario().getUsuario(), tareaRequestDto.getTarea().getUsuario().getPassword());
                if (listaUsuarios.size() == 0) {
                    validarLogin = false;
                } 
                if(!validarLogin){
                    error = 1;
                    descripcion = "No se pudo validar el login. Verifique sus credenciales";
                }else{
                    System.out.println("poai 4");
                    if(iTareaImpl.tareaPorDescripcionYusuario(tareaRequestDto.getTarea().getDescripcion(),
                            tareaRequestDto.getTarea().getUsuario().getIdusuario()).size()>0){
                        error = 1;
                        descripcion = "Esta tarea ya esta asociada a este usuario";
                    }else{
                        System.out.println("poai 5");
                        System.out.println(tareaRequestDto.getTarea().toString());
                        
                        tarea = iTareaImpl.registrarTarea(tareaRequestDto.getTarea());
                        System.out.println("poai 6");
                        List<Comentariosportarea> comentariosportareasListBody = tareaRequestDto.getTarea().getComentariosportareasList();
                        for (int i = 0; i < comentariosportareasListBody.size(); i++) {
                            System.out.println("poai 7");
                            Comentariosportarea cptBody=comentariosportareasListBody.get(i);
                            cptBody.setIdtarea(tarea);
                            comentariosportareaImpl.registrarComentario(cptBody);
                            System.out.println("poai 8");
                        }                                                
                    }
                }                    
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            WsApplication.registrarErrorLog(e.toString());
            descripcion = "Se presentó un error al tratar de registrar la tarea";
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setTarea(tarea);
        return tareaResponseDto;
    }
    
    
    
    /*Metodo usado para apoyo en pruebas, se debe eliminar para producción*/
    @GetMapping(value = "/tarea/encriptar")
    public @ResponseBody
    TareaBucket encriptarUsuario(@RequestBody TareaRequestDto tareaRequest) {
        String dataEncripted = "";
        TareaBaseDto tareaResponse = new TareaBaseDto();
        tareaResponse.setTareaRequest(tareaRequest);
        tareaResponse.setExpdate(Utils.formatedDate());
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            byte[] cipherText = AESEncryption.encryptText(new Gson().toJson(tareaResponse), secKey);
            dataEncripted = AESEncryption.bytesToHex(cipherText);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        TareaBucket tareaBucket = new TareaBucket();
        tareaBucket.setEncriptedData(dataEncripted);
        tareaBucket.setTareaBaseDto(tareaResponse);
        return tareaBucket;
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
