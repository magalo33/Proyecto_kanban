package com.sophos.ws.web;

import com.sophos.ws.WsApplication;
import com.sophos.ws.utils.AESEncryption;
import com.sophos.ws.utils.ConfiguracionUtilValues;
import javax.crypto.SecretKey;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping(value = "/api/kanban")
@RestController
@CrossOrigin(origins="*",methods={RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class UtilsController {
    @GetMapping(value = "/encriptardato/{dato}")
    public @ResponseBody
    String encriptarDato(@PathVariable("dato") String dato) {
        String encripted = "";
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            byte[] cipherText = AESEncryption.encryptText(dato, secKey);
            encripted = AESEncryption.bytesToHex(cipherText);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return encripted;
    }
    
   @GetMapping(value = "/desencriptardato/{dato}")
    public @ResponseBody
    String desencriptarDato(@PathVariable("dato") String dato) {
        String decriptedData = "";
        try {
            String keyText = ConfiguracionUtilValues.KEY;
            SecretKey secKey = AESEncryption.getSecretEncryptionKey(keyText);
            decriptedData = AESEncryption.decryptText(
                    AESEncryption.parseHexBinary(dato),
                    secKey);
        } catch (Exception ex) {
            WsApplication.registrarErrorLog(ex.toString());
        }
        return decriptedData;
    }    

}
