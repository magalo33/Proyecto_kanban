package com.sophos.ws.utils;

import com.google.gson.Gson;
import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Estado;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.dto.tarea.EditarTareaBaseRequestDto;
import com.sophos.ws.dto.tarea.EditarTareaRequestDto;
import com.sophos.ws.dto.tarea.TareaBaseDto;
import com.sophos.ws.dto.tarea.TareaRequestDto;
import com.sophos.ws.dto.tarea.VerificacionTareaResponseDto;
import com.sophos.ws.dto.usuario.UsuarioBaseDto;
import com.sophos.ws.dto.usuario.UsuarioRequestDto;
import com.sophos.ws.dto.usuario.UsuarioResponseDto;
import com.sophos.ws.impl.UsuarioImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.crypto.SecretKey;

public class Utils {

    /**
     * Lee las propiedades desde el archivo origen
     * 
     * @param String rutaProperties
     * @return Properties configs
     */
    public static Properties getConfigs(String rutaProperties) throws FileNotFoundException, IOException {
        Properties configs = new Properties();
        configs.load(new FileInputStream(rutaProperties));
        return configs;
    }

    /**
     * Retorna la fecha y hora actual en formato yyyy-MM-dd HH:mm:ss
     * 
     * @param Propiedades propiedades
     * @return String formated_date
    */
    public static String formatedDate(Propiedades propiedades) {
        @SuppressWarnings("UnusedAssignment")
        String formated_date = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(propiedades.getFormatoFechaHora());
        formated_date = sdf.format(cal.getTime());
        return formated_date;
    }

    /**
     * Retorna el dato des encriptado
     * 
     * @param String encriptedData
     * @return String decriptedData
    */
    public static String decryptText(String encriptedData) {
        String decriptedData = "";
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            decriptedData = AESEncryption.decryptText(
                    AESEncryption.parseHexBinary(encriptedData),
                    secKey);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return decriptedData;
    }

    /* 
     * Método que valida el body contra el header en el registro de usuario
     * 
     * @param UsuarioBaseDto usuarioBaseDto
     * @param UsuarioRequestDto usuarioRequest
     * @return boolean parametrosValidos
    */
    public static boolean validarParametrosRegistroUsuario(
            UsuarioBaseDto usuarioBaseDto,
            UsuarioRequestDto usuarioRequest) {
        boolean parametrosValidos = false;
        if (usuarioBaseDto.getUsuarioRequest().getUsuario().getUsuario().equals(
                usuarioRequest.getUsuario().getUsuario())
                && usuarioBaseDto.getUsuarioRequest().getUsuario().getPassword().equals(
                        decryptText(usuarioRequest.getUsuario().getPassword()))) {
            parametrosValidos = true;
        }
        return parametrosValidos;
    }

    /* 
     * Valida que no haya transcurrido mas tiempo del estipulado en el time_out entre
     * la recepción del request y el momento actual
     * 
     * @param String tiempoParam
     * @param Propiedades propiedades
     * @return boolean tiempoValido
    */
    public static boolean validarTiempoDeEspera(String tiempoParam,Propiedades propiedades) {
        boolean tiempoValido = false;
        String formato = propiedades.getFormatoFechaHora();
        int timeOut = Integer.parseInt(ConfiguracionUtilValues.TIME_OUT);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(formato);
            Date date = sdf.parse(tiempoParam);
            Calendar cal = Calendar.getInstance();
            long t = ((cal.getTimeInMillis() - date.getTime()) / 1000);
            if (t <= timeOut) {
                tiempoValido = true;
            }
        } catch (ParseException ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return tiempoValido;
    }

    /* 
     * Método que valida el body contra el header en el registro de tarea
     * 
     * @param TareaBaseDto tareaBaseDto
     * @param TareaRequestDto tareaRequestBody
     * @param Propiedades propiedades
     * @return VerificacionTareaResponseDto verificacionTareaResponseDto
    */
    @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
    public static VerificacionTareaResponseDto validarParametrosRegistroTarea(
            TareaBaseDto tareaBaseDto,
            TareaRequestDto tareaRequestBody,
            Propiedades propiedades) {
        VerificacionTareaResponseDto verificacionTareaResponseDto = new VerificacionTareaResponseDto();
        int error = 1;
        String descripcion =  propiedades.getErrorParametrosRegistro();
        try {
            TareaRequestDto tareaRequestHeader = tareaBaseDto.getTareaRequest();
            String expdate = tareaBaseDto.getExpdate();
            Tarea tareaHeader = tareaRequestHeader.getTarea();
            Tarea tareaBody   = tareaRequestBody.getTarea();
            Long usuarioHeader = tareaHeader.getIdusuario();
            Long usuarioBody   = tareaBody.getIdusuario();
            Estado estadoHeader   = tareaHeader.getEstado();
            Estado estadoBody     = tareaBody.getEstado();
            List<Comentariosportarea> comentariosportareasListHeader = tareaHeader.getComentariosportareasList();
            List<Comentariosportarea> comentariosportareasListBody   = tareaBody.getComentariosportareasList();
            if (validarTiempoDeEspera(expdate,propiedades)) {
                if (tareaHeader.getDescripcion().equals(tareaBody.getDescripcion())) {
                    if (usuarioHeader == usuarioBody) {
                        if (estadoHeader.getIdestado() == estadoBody.getIdestado()) {
                            if (comentariosportareasListHeader.size() == comentariosportareasListBody.size()) {
                                error = 0;
                                descripcion = propiedades.getVerificaciónParametrosOk();
                                for (int i = 0; i < comentariosportareasListHeader.size(); i++) {
                                    Comentariosportarea cptHeader=comentariosportareasListHeader.get(i);
                                    Comentariosportarea cptBody=comentariosportareasListBody.get(i);
                                    if(!cptHeader.getComentario().equals(cptBody.getComentario())){
                                        error = 1;
                                        descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getComentarios());
                                        break;
                                    }
                                }
                            } else {
                                descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getComentarios());
                            }
                        } else {
                            descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getEstado());
                        }
                    } else {
                        descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getUsuario());
                    }
                } else {
                    descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getDescripcion());
                }
            } else {
                descripcion = propiedades.getTimeOutExcedido();
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getErrorTecnico());
        }
        verificacionTareaResponseDto.setDescripcion(descripcion);
        verificacionTareaResponseDto.setError(error);
        return verificacionTareaResponseDto;
    }
    
    
    
    /* 
     * Método que valida el body contra el header en la edición de tarea
     * 
     * @param EditarTareaBaseRequestDto tareaBaseDto
     * @param EditarTareaRequestDto editarTareaRequestBody
     * @param Propiedades propiedades
     * @return VerificacionTareaResponseDto verificacionTareaResponseDto
    */
    @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
    public static VerificacionTareaResponseDto validarParametrosEdicionTarea(
            EditarTareaBaseRequestDto tareaBaseDto,
            EditarTareaRequestDto editarTareaRequestBody,
            Propiedades propiedades) {
        VerificacionTareaResponseDto verificacionTareaResponseDto = new VerificacionTareaResponseDto();
        int error = 1;
        String descripcion =  propiedades.getErrorParametrosEdicion();
        try {
            EditarTareaRequestDto editarTareaRequestDtoHeader = tareaBaseDto.getEditarTareaRequestDto();
            String expdate = tareaBaseDto.getExpdate();
            Tarea tareaHeader = editarTareaRequestDtoHeader.getTarea();
            Tarea tareaBody   = editarTareaRequestBody.getTarea();
            Long usuarioHeader = tareaHeader.getIdusuario();
            Long usuarioBody   = tareaBody.getIdusuario();
            Estado estadoHeader   = tareaHeader.getEstado();
            Estado estadoBody     = tareaBody.getEstado();
            if (validarTiempoDeEspera(expdate,propiedades)) {
                if (tareaHeader.getDescripcion().equals(tareaBody.getDescripcion())) {
                    if (usuarioHeader == usuarioBody) {
                        if (estadoHeader.getIdestado() == estadoBody.getIdestado()) {
                            error = 0;
                            descripcion = propiedades.getVerificaciónParametrosOk();
                        } else {
                            descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getEstado());
                        }
                    } else {
                        descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getUsuario());
                    }
                } else {
                    descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getDescripcion());
                }                
            } else {
                descripcion = propiedades.getTimeOutExcedido();
            }
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = propiedades.getDatosRegistroNoConcuerdan().concat(propiedades.getErrorTecnico());
        }
        verificacionTareaResponseDto.setDescripcion(descripcion);
        verificacionTareaResponseDto.setError(error);
        return verificacionTareaResponseDto;
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
    public static UsuarioResponseDto validarLogin(
            UsuarioBaseDto usuarioBaseDto,
            UsuarioRequestDto usuarioRequest,
            UsuarioImpl usuarioImpl,
            Propiedades propiedades){
        int error = 1;
        String descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getValidarLogin());
        Usuario usuario = null;
        UsuarioResponseDto response = new UsuarioResponseDto();
        try {
            if (Utils.validarParametrosRegistroUsuario(usuarioBaseDto, usuarioRequest)) {
                if (Utils.validarTiempoDeEspera(usuarioBaseDto.getExpdate(),propiedades)) {
                    List<Usuario> listaUsuarios = usuarioImpl.findByUsuarioAndPassword(usuarioRequest.getUsuario().getUsuario(), usuarioRequest.getUsuario().getPassword());
                    if (listaUsuarios.size() == 0) {
                        descripcion = propiedades.getSinDatosRelacionadosACredenciales();
                    }else{
                        usuario = listaUsuarios.get(0);
                        usuario.setPassword(propiedades.getMascaraPassword());
                        error = 0;
                        descripcion = propiedades.getLoginOk();
                    }
                } else {
                    descripcion = propiedades.getTimeOutExcedido();
                }
            } else {
                descripcion = propiedades.getDatosRegistroNoConcuerdan();
            }
        } catch (Exception e) {  
            descripcion = propiedades.getPrefijoErrorTecnico().concat(propiedades.getValidarLogin());
            WsApplication.registrarErrorLog(e.toString());
        }
        response.setDescripcion(descripcion);
        response.setError(error);
        response.setUsuario(usuario);
        return response;
    }
    

}
