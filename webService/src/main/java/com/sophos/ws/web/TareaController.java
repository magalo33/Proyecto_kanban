package com.sophos.ws.web;

import com.google.gson.Gson;
import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.dto.tarea.EditarTareaBaseRequestDto;
import com.sophos.ws.dto.tarea.EditarTareaRequestDto;
import com.sophos.ws.utils.Utils;
import com.sophos.ws.dto.tarea.TareaBaseDto;
import com.sophos.ws.dto.tarea.TareaBucket;
import com.sophos.ws.dto.tarea.TareaEdicionBucket;
import com.sophos.ws.dto.tarea.TareaEditarBaseDto;
import com.sophos.ws.dto.tarea.TareaRequestDto;
import com.sophos.ws.dto.tarea.TareaResponseDto;
import com.sophos.ws.dto.tarea.VerificacionTareaResponseDto;
import com.sophos.ws.dto.usuario.UsuarioBaseDto;
import com.sophos.ws.dto.usuario.UsuarioRequestDto;
import com.sophos.ws.dto.usuario.UsuarioResponseDto;
import com.sophos.ws.impl.ComentariosportareaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sophos.ws.impl.TareaImpl;
import com.sophos.ws.impl.UsuarioImpl;
import com.sophos.ws.utils.AESEncryption;
import com.sophos.ws.utils.ConfiguracionUtilValues;
import com.sophos.ws.utils.Propiedades;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/kanban")
@RestController
public class TareaController {

    @Autowired
    private TareaImpl tareaImpl;
    @Autowired
    private UsuarioImpl usuarioImpl;
    @Autowired
    private ComentariosportareaImpl comentariosportareaImpl;
    @Autowired
    private Propiedades propiedades;
    

    /*request
    {
	"tarea":{
        "idusuario":42,
		"descripcion":"quinta revision de la cosa",
		"comentariosportareasList":[
			{
				"comentario":"comentario de la quinta revision de la cosa"
			}
		],
		"estado":{
			"idestado":1,
            "descripcion": "por_hacer"
		}
	}
}
    */
    
    /*Metodo de registro de tarea, asociada a un usuario*/
    @PostMapping(value = "/tarea")
    public @ResponseBody
    UsuarioResponseDto registrarTarea(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody TareaRequestDto tareaRequestDto) {
        int error = 0;
        String descripcion = propiedades.getRegistroTareaOk();
        Tarea tarea = null;
        Gson gson = new Gson();
        List<Tarea> listaTareas = null;
        Usuario usuario = null;
        UsuarioResponseDto response = new UsuarioResponseDto();
        try {
            TareaBaseDto tareaBaseDto = gson.fromJson(Utils.decryptText((headers.get(propiedades.getXSessionToken())).get(0)), TareaBaseDto.class);
            VerificacionTareaResponseDto verificaciontareas = Utils.validarParametrosRegistroTarea(tareaBaseDto, tareaRequestDto,propiedades);
            if (verificaciontareas.getError() > 0) {
                error = 1;
                descripcion = verificaciontareas.getDescripcion();
            } else {
                if (tareaImpl.tareaPorDescripcionYusuario(tareaRequestDto.getTarea().getDescripcion(),
                        tareaRequestDto.getTarea().getIdusuario()).size() > 0) {
                    error = 1;
                    descripcion = propiedades.getTareaYaUsuario();
                } else {
                    tarea = tareaImpl.registrarTarea(tareaRequestDto.getTarea());
                    List<Comentariosportarea> comentariosportareasListBody = 
                            tareaRequestDto.getTarea().getComentariosportareasList();
                    for (int i = 0; i < comentariosportareasListBody.size(); i++) {
                        Comentariosportarea cptBody = comentariosportareasListBody.get(i);
                        cptBody.setIdtarea(tarea.getIdtarea());
                        comentariosportareaImpl.registrarComentario(cptBody);
                    }
                    usuario = this.usuarioImpl.usuarioPorId(tareaRequestDto.getTarea().getIdusuario());
                    usuario.setPassword(propiedades.getMascaraPassword());
                    error = 0;
                    descripcion = propiedades.getLoginOk();
                    
                    //listaTareas = tareaImpl.listaTareas(tareaRequestDto.getTarea().getIdusuario());
                }
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getErrorRegistro().concat(propiedades.getLa().concat(propiedades.getTarea()));
        }
        response.setDescripcion(descripcion);
        response.setError(error);
        response.setUsuario(usuario);
        return response;        
    }
    
    /*Metodo de actualizacion de tarea*/
    @PutMapping(value = "/tarea")
    public @ResponseBody
    TareaResponseDto edicionTarea(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody EditarTareaRequestDto editarTareaRequestDto) {
        int error = 0;
        String descripcion = propiedades.getEdicionTareaOk();
        Tarea tarea = null;
        Gson gson = new Gson();
        List<Tarea> listaTareas = null;
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        try {
            EditarTareaBaseRequestDto tareaBaseDto = gson.fromJson(Utils.decryptText((headers.get(propiedades.getXSessionToken())).get(0)), EditarTareaBaseRequestDto.class);
            VerificacionTareaResponseDto verificaciontareas = Utils.validarParametrosEdicionTarea(tareaBaseDto, editarTareaRequestDto ,propiedades);
            if (verificaciontareas.getError() > 0) {
                error = 1;
                descripcion = verificaciontareas.getDescripcion();
            } else {
                tareaImpl.editarTarea(editarTareaRequestDto.getTarea());
                listaTareas = tareaImpl.listaTareas(editarTareaRequestDto.getTarea().getIdusuario());                
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getErrorEdicion().concat(propiedades.getLa().concat(propiedades.getTarea()));
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setListaTareas(listaTareas);
        return tareaResponseDto;
    }
    
    
    /*Metodo de eliminación de tarea*/
    @DeleteMapping(value = "/tarea")
    public @ResponseBody
    TareaResponseDto eliminacionTarea(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody EditarTareaRequestDto editarTareaRequestDto) {
        int error = 0;
        String descripcion = propiedades.getEliminacionTareaOk();
        Tarea tarea = null;
        Gson gson = new Gson();
        List<Tarea> listaTareas = null;
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        try {
            EditarTareaBaseRequestDto tareaBaseDto = gson.fromJson(Utils.decryptText((headers.get(propiedades.getXSessionToken())).get(0)), EditarTareaBaseRequestDto.class);
            VerificacionTareaResponseDto verificaciontareas = Utils.validarParametrosEdicionTarea(tareaBaseDto, editarTareaRequestDto ,propiedades);
            if (verificaciontareas.getError() > 0) {
                error = 1;
                descripcion = verificaciontareas.getDescripcion();
            } else {
                comentariosportareaImpl.eliminarComentarioPorTarea(editarTareaRequestDto.getTarea().getIdtarea());
                tareaImpl.eliminarTarea(editarTareaRequestDto.getTarea());
                listaTareas = tareaImpl.listaTareas(editarTareaRequestDto.getTarea().getIdusuario());                
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getErrorEliminacion().concat(propiedades.getLa().concat(propiedades.getTarea()));
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setListaTareas(listaTareas);
        return tareaResponseDto;
    }

    
    /*request
        {
            "usuario":{
                "usuario":"otro_usuario__2_garzonq@gmail.com",
                "password":"49292684CA6A77398E35C7C738EC125A"
            }
        }
    */
    /*Obtiene la lista de atreas asociadas a un usuario*/
    @PostMapping(value = "/tarea/listatreas")
    public @ResponseBody
    TareaResponseDto obtenerTarea(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody UsuarioRequestDto usuarioRequest) {
        int error = 1;
        String descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getObtenerListaTareasUsuario());
        Gson gson = new Gson();
        List<Tarea> listaTareas = null;
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        UsuarioResponseDto usuarioResponse = new UsuarioResponseDto();
        try {
            UsuarioBaseDto usuarioBaseDto = gson.fromJson(
                    Utils.decryptText((headers.get(propiedades.getXSessionToken())).get(0)),
                    UsuarioBaseDto.class);
            usuarioResponse = Utils.validarLogin(usuarioBaseDto, usuarioRequest, usuarioImpl,propiedades);
            error = usuarioResponse.getError();
            if (usuarioResponse.getError() == 0) {
                listaTareas = tareaImpl.listaTareas(usuarioResponse.getUsuario().getIdusuario());
                if (listaTareas.size() == 0) {
                    descripcion = propiedades.getUsuarioSinTareasAsociadas();
                } else {
                    descripcion = propiedades.getListaTareasEncontrada();
                }
            } else {
                descripcion = usuarioResponse.getDescripcion();
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getLoguearUsuario());
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setListaTareas(listaTareas);
        return tareaResponseDto;
    }
    
    
    /*Obtiene una tarea por su id*/
    @GetMapping(value = "/tarea/{idtarea}")
    public @ResponseBody
    TareaResponseDto obtenerTareaPorId(@PathVariable("idtarea") long idtarea) {
        int error = 1;
        String descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getObtenerListaTareasPorId());
        Gson gson = new Gson();
        List<Tarea> listaTareas = null;
        TareaResponseDto tareaResponseDto = new TareaResponseDto();
        UsuarioResponseDto usuarioResponse = new UsuarioResponseDto();
        try {
            listaTareas = tareaImpl.tareasPorId(idtarea);
            if (listaTareas.size() == 0) {
                descripcion = propiedades.getListaTareasNoEncontrada();
            } else {
                error = 0;
                descripcion = propiedades.getListaTareasEncontrada();
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getObtenerListaTareasPorId());
        }
        tareaResponseDto.setDescripcion(descripcion);
        tareaResponseDto.setError(error);
        tareaResponseDto.setListaTareas(listaTareas);
        return tareaResponseDto;
    }

    /*Metodo usado para apoyo en pruebas, se debe eliminar para producción*/
    @GetMapping(value = "/tarea/encriptar")
    public @ResponseBody
    TareaBucket encriptarUsuario(@RequestBody TareaRequestDto tareaRequest) {
        String dataEncripted = "";
        TareaBaseDto tareaResponse = new TareaBaseDto();
        tareaResponse.setTareaRequest(tareaRequest);
        tareaResponse.setExpdate(Utils.formatedDate(propiedades));
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
    
    
    /*Metodo usado para apoyo en pruebas, se debe eliminar para producción*/
    @GetMapping(value = "/tarea/encriptar2")
    public @ResponseBody
    TareaEdicionBucket encriptarTarea(@RequestBody EditarTareaRequestDto editarTareaRequestDto) {
        String dataEncripted = "";
        TareaEditarBaseDto tareaResponse = new TareaEditarBaseDto();
        tareaResponse.setEditarTareaRequestDto(editarTareaRequestDto);
        tareaResponse.setExpdate(Utils.formatedDate(propiedades));
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            byte[] cipherText = AESEncryption.encryptText(new Gson().toJson(tareaResponse), secKey);
            dataEncripted = AESEncryption.bytesToHex(cipherText);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        TareaEdicionBucket tareaBucket = new TareaEdicionBucket();
        tareaBucket.setEncriptedData(dataEncripted);
        tareaBucket.setTareaBaseDto(tareaResponse);
        return tareaBucket;
    }

    
    
    /* 
     * Método que valida la validez de los parametros y login de un usuario
     * 
     * @param UsuarioBaseDto usuarioBaseDto
     * @param UsuarioRequestDto usuarioRequest
     * @param UsuarioImpl usuarioImpl
     * @param Propiedades propiedades
     * @return VerificacionTareaResponseDto verificacionTareaResponseDto
    */
    
   
}
