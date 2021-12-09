package com.sophos.ws;

import com.sophos.ws.utils.ConfiguracionAplicaccion;
import com.sophos.ws.utils.ConfiguracionUtilValues;
import com.sophos.ws.utils.Log;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WsApplication {

    private static Log log;
    public static boolean iniciado = false;

    public static void main(String[] args) {
        try {
            ConfiguracionAplicaccion.getConfiguracion();
            WsApplication.log = new Log(ConfiguracionUtilValues.LOG_FILE_PATH);
            try {
                Thread.sleep(2000);
                iniciado = true;
            } catch (InterruptedException ex) {
                Logger.getLogger(WsApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
            WsApplication.registrarInfoLog("proceso iniciado");
            SpringApplication.run(WsApplication.class, args);
        } catch (IOException ex) {
            registrarErrorLog(ex.toString());
        }

    }

    public static void registrarErrorLog(String msg) {
        try {
            if (iniciado) {
                WsApplication.log.addLine("[ERROR] " + msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(WsApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void registrarInfoLog(String msg) {
        try {
            if (iniciado) {
                WsApplication.log.addLine("[INFO] " + msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(WsApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
