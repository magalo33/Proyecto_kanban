package com.sophos.ws.web;

import com.google.gson.Gson;
import com.sophos.ws.WsApplication;
import com.sophos.ws.domain.Usuario;
import com.sophos.ws.dto.usuario.UsuarioBucket;
import com.sophos.ws.dto.usuario.UsuarioRequestDto;
import com.sophos.ws.dto.usuario.UsuarioBaseDto;
import com.sophos.ws.dto.usuario.UsuarioResponseDto;
import com.sophos.ws.impl.UsuarioImpl;
import com.sophos.ws.utils.AESEncryption;
import com.sophos.ws.utils.ConfiguracionUtilValues;
import com.sophos.ws.utils.Propiedades;
import com.sophos.ws.utils.Utils;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/kanban")
@RestController
@CrossOrigin(origins="*",methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class UsuarioController {

    @Autowired
    private UsuarioImpl usuarioImpl;
    @Autowired
    private Propiedades propiedades;

    /*Metodo que retorna la lista completa de usuarios*/
    @GetMapping(value = "/usuario")
    public @ResponseBody
    List<Usuario> listaUsuarios() {
        WsApplication.registrarInfoLog("Llamado a la lista de usuarios");
        List<Usuario> listaUsuarios = usuarioImpl.listaUsuarios();
        for(Usuario usuario: listaUsuarios){
            usuario.setPassword("...");
        }
        return listaUsuarios;
    }

    
    /*Metodo para registrar un usuario*/
    @PostMapping(value = "/usuario")
    public @ResponseBody
    UsuarioResponseDto registrarUsuario(
            @RequestHeader MultiValueMap<String, String> headers,
            @RequestBody UsuarioRequestDto usuarioRequest) {
        int error = 1;
        String descripcion = "";
        Usuario usuario = null;
        Gson gson = new Gson();
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        try {
            UsuarioBaseDto usuarioBaseDto = gson.fromJson(
                    Utils.decryptText((headers.get("x-session-token")).get(0)),
                    UsuarioBaseDto.class);
            if (Utils.validarParametrosRegistroUsuario(usuarioBaseDto, usuarioRequest)) {
                if (Utils.validarTiempoDeEspera(usuarioBaseDto.getExpdate(),propiedades)) {
                    if (usuarioImpl.listarPorusuario(usuarioRequest.getUsuario().getUsuario()).isEmpty()) {
                        usuario = usuarioImpl.registrarUsuario(usuarioRequest.getUsuario());
                        usuario.setPassword("...");
                        error = 0;
                        descripcion = "Registro  de usuario ok";
                    } else {
                        descripcion = "Este usuario ya se encuentra registrado";
                    }
                } else {
                    descripcion = "Tiempo máximo de espera sobrepasado";
                }
            } else {
                descripcion = "Los datos de registro no concuerdan";
            }

        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
            descripcion = "Se presentó un error al tratar de registrar el usuario";
        }
        usuarioResponseDto.setUsuario(usuario);
        usuarioResponseDto.setError(error);
        usuarioResponseDto.setDescripcion(descripcion);
        WsApplication.registrarInfoLog(usuarioResponseDto.getDescripcion().concat(" ").concat(new Gson().toJson(usuarioRequest)));
        return usuarioResponseDto;
    }

    /*Valida login de un usuario*/
    @PostMapping(value = "/usuario/login")
    public @ResponseBody
    UsuarioResponseDto validarLoginUsuario(@RequestHeader MultiValueMap<String, String> headers,
            @RequestBody UsuarioRequestDto usuarioRequest) {
        Gson gson = new Gson();
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setDescripcion("Se presentó un error técnico al tratar de validar el login");
        usuarioResponseDto.setError(1);
        usuarioResponseDto.setUsuario(null);
        try {
            UsuarioBaseDto usuarioBaseDto = gson.fromJson(
                    Utils.decryptText((headers.get("x-session-token")).get(0)),UsuarioBaseDto.class);       
            usuarioResponseDto = Utils.validarLogin(usuarioBaseDto,usuarioRequest,usuarioImpl,propiedades);      
        } catch (Exception e) {
            WsApplication.registrarErrorLog(e.toString());
        }
        WsApplication.registrarInfoLog(usuarioResponseDto.getDescripcion().concat(" ").concat(new Gson().toJson(usuarioRequest)));
        return usuarioResponseDto;
    }

    
    /*Retorna un usuario por su id*/
    @GetMapping(value = "/usuario/{idusuario}")
    public @ResponseBody
    Usuario usuarioporId(@PathVariable("idusuario") long idusuario) {
        WsApplication.registrarInfoLog("Buscar usuario por id ".concat(String.valueOf(idusuario)));
        Usuario usuario = usuarioImpl.usuarioPorId(idusuario);
        usuario.setPassword("...");
        return usuario;
    }

    
    
    /*Metodo usado para apoyo en pruebas, se debe eliminar para producción*/
    @GetMapping(value = "/usuario/encriptar")
    public @ResponseBody
    UsuarioBucket encriptarUsuario(@RequestBody UsuarioRequestDto usuarioRequest) {
        String dataEncripted = "";
        UsuarioBaseDto usuarioResponse = new UsuarioBaseDto();
        usuarioResponse.setUsuarioRequest(usuarioRequest);
        usuarioResponse.setExpdate(Utils.formatedDate(propiedades));
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            byte[] cipherText = AESEncryption.encryptText(new Gson().toJson(usuarioResponse), secKey);
            dataEncripted = AESEncryption.bytesToHex(cipherText);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        UsuarioBucket usuarioBucket = new UsuarioBucket();
        usuarioBucket.setEncriptedData(dataEncripted);
        usuarioBucket.setUsuarioResponseDto(usuarioResponse);
        return usuarioBucket;
    }

    /*Metodo usado para apoyo en pruebas, se debe eliminar para producción*/
    @GetMapping(value = "/usuario/desencriptar")
    public @ResponseBody
    String desencriptarDato(@RequestBody UsuarioBucket usuarioBucket) {
        String decriptedData = "";
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            decriptedData = AESEncryption.decryptText(
                    AESEncryption.parseHexBinary(usuarioBucket.getEncriptedData()),
                    secKey);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return decriptedData;
    }

}
