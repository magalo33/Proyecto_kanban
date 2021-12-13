package com.sophos.ws.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Propiedades {
    @Value("${lista_tareas_encontrada}")
    private String  listaTareasEncontrada;
    
    @Value("${lista_tareas_no_encontrada}")
    private String  listaTareasNoEncontrada;
    
    @Value("${registro_tarea_ok}")
    private String registroTareaOk;
    
    @Value("${x_session_token}")
    private String xSessionToken;
    
    @Value("${tarea_ya_usuario}")
    private String tareaYaUsuario;
    
    @Value("${error_registro}")
    private String errorRegistro;
    
    @Value("${el}")
    private String el;
    
    @Value("${la}")
    private String la;

    @Value("${los}")
    private String los;
    
    @Value("${las}")
    private String las;
    
    @Value("${tarea}")
    private String tarea;

    @Value("${usuario}")
    private String usuario;    
    
    @Value("${prefijo_error_tecnico}")
    private String prefijoErrorTecnico;    
    
    @Value("${obtener_lista_tareas_usuario}")
    private String obtenerListaTareasUsuario; 

    @Value("${usuario_sin_tareas_asociadas}")
    private String usuarioSinTareasAsociadas; 

    @Value("${loguear_usuario}")
    private String loguearUsuario;     

    @Value("${obtener_lista_tareas_por_id}")
    private String obtenerListaTareasPorId;         
    
    @Value("${edicion_tarea_ok}")
    private String edicionTareaOk;  

    @Value("${editar_tarea}")
    private String editarTarea;  
    
    @Value("${formato_fecha_hora}")
    private String formatoFechaHora;  
    
    @Value("${error_parametros_registro}")
    private String errorParametrosRegistro;  
    
    @Value("${error_parametros_edicion}")
    private String errorParametrosEdicion;      
    
    @Value("${verificacion_parametros_Ok}")
    private String verificaciónParametrosOk;  
    
    @Value("${datos_registro_no_concuerdan}")
    private String datosRegistroNoConcuerdan;      
    
    @Value("${comentarios}")
    private String comentarios;

    @Value("${estado}")
    private String estado;

    @Value("${error_tecnico}")
    private String errorTecnico;    
    
    @Value("${descripcion}")
    private String descripcion;    
    
    @Value("${time_out_excedido}")
    private String timeOutExcedido;    

    @Value("${validar_login}")
    private String validarLogin;        
    
    @Value("${sin_datos_relacionados_a_credenciales}")
    private String sinDatosRelacionadosACredenciales;      
    
    @Value("${login_ok}")
    private String loginOk;      
    
    @Value("${mascara_password}")
    private String mascaraPassword;     
    
    @Value("${error_edicion}")
    private String errorEdicion;         
    
    @Value("${error_eliminacion}")
    private String errorEliminacion;         
    
    @Value("${eliminacion_tarea_ok}")
    private String eliminacionTareaOk;         
    
}
