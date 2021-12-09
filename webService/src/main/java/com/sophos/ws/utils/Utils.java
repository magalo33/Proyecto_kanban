package com.sophos.ws.utils;

import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Comentariosportarea;
import com.sophos.ws.domain.Estado;
import com.sophos.ws.domain.Tarea;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.dto.tarea.TareaBaseDto;
import com.sophos.ws.dto.tarea.TareaRequestDto;
import com.sophos.ws.dto.tarea.VerificacionTareaResponseDto;
import com.sophos.ws.dto.usuario.UsuarioBaseDto;
import com.sophos.ws.dto.usuario.UsuarioRequestDto;
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

    /*Lee las propiedades desde el archivo origen*/
    public static Properties getConfigs(String rutaProperties) throws FileNotFoundException, IOException {
        Properties configs = new Properties();
        configs.load(new FileInputStream(rutaProperties));
        return configs;
    }

    /*Retorna la fecha y hora actual en formato yyyy-MM-dd HH:mm:ss*/
    public static String formatedDate() {
        @SuppressWarnings("UnusedAssignment")
        String formated_date = "";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formated_date = sdf.format(cal.getTime());
        return formated_date;
    }

    /*Retorna el dato des encriptado*/
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

    /*Método que valida el body contra el header en el registro de usuario*/
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

    /*Valida que no haya transcurrido mas tiempo del estipulado en el time_out entre 
    la recepción del request y el momento actual*/
    public static boolean validarTiempoDeEspera(String tiempoParam) {
        boolean tiempoValido = false;
        String formato = "yyyy-MM-dd HH:mm:ss";
        int timeOut = Integer.parseInt(ConfiguracionUtilValues.TIME_OUT);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern(formato);
            Date date = sdf.parse(tiempoParam);

            Calendar cal = Calendar.getInstance();
            String strDate = sdf.format(cal.getTime());
            long t = ((cal.getTimeInMillis() - date.getTime()) / 1000);
            if (t <= timeOut) {
                tiempoValido = true;
            }
        } catch (ParseException ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return tiempoValido;
    }

    /*Método que valida el body contra el header en el registro de tarea*/
    @SuppressWarnings({"BoxedValueEquality", "NumberEquality"})
    public static VerificacionTareaResponseDto validarParametrosRegistroTarea(
            TareaBaseDto tareaBaseDto,
            TareaRequestDto tareaRequestBody) {
        VerificacionTareaResponseDto verificacionTareaResponseDto = new VerificacionTareaResponseDto();
        int error = 1;
        String descripcion = "Error verificando parametros de registro";
        try {
            TareaRequestDto tareaRequestHeader = tareaBaseDto.getTareaRequest();
            String expdate = tareaBaseDto.getExpdate();
            Tarea tareaHeader = tareaRequestHeader.getTarea();
            Tarea tareaBody   = tareaRequestBody.getTarea();
            Usuario usuarioHeader = tareaHeader.getUsuario();
            Usuario usuarioBody   = tareaBody.getUsuario();
            Estado estadoHeader   = tareaHeader.getIdestado();
            Estado estadoBody     = tareaBody.getIdestado();
            List<Comentariosportarea> comentariosportareasListHeader = tareaHeader.getComentariosportareasList();
            List<Comentariosportarea> comentariosportareasListBody   = tareaBody.getComentariosportareasList();
            if (validarTiempoDeEspera(expdate)) {
                if (tareaHeader.getDescripcion().equals(tareaBody.getDescripcion())) {
                    if (usuarioHeader.getIdusuario() == usuarioBody.getIdusuario()) {
                        if (estadoHeader.getIdestado() == estadoBody.getIdestado()) {
                            if (comentariosportareasListHeader.size() == comentariosportareasListBody.size()) {
                                error = 0;
                                descripcion = "Verificación de parametros Ok";
                                for (int i = 0; i < comentariosportareasListHeader.size(); i++) {
                                    Comentariosportarea cptHeader=comentariosportareasListHeader.get(i);
                                    Comentariosportarea cptBody=comentariosportareasListBody.get(i);
                                    if(!cptHeader.getComentario().equals(cptBody.getComentario())){
                                        error = 1;
                                        descripcion = "Los datos de registro no concuerdan(comentarios)";
                                        break;
                                    }
                                }
                            } else {
                                descripcion = "Los datos de registro no concuerdan(comentarios)";
                            }
                        } else {
                            descripcion = "Los datos de registro no concuerdan(estado)";
                        }
                    } else {
                        descripcion = "Los datos de registro no concuerdan(usuario)";
                    }
                } else {
                    descripcion = "Los datos de registro no concuerdan(descripción)";
                }
            } else {
                descripcion = "Time_out excedido";
            }
        } catch (Exception e) {
            descripcion = "Los datos de registro no concuerdan(Error técnico)";
        }
        verificacionTareaResponseDto.setDescripcion(descripcion);
        verificacionTareaResponseDto.setError(error);
        return verificacionTareaResponseDto;
    }

}
