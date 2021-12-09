package com.sophos.ws.utils;


import com.sophos.ws.WsApplication;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfiguracionAplicaccion {

    public static void getConfiguracion() throws IOException {
        try {
            /*Obtiene propiedades desde el archivo de origen de las mismas*/
            Properties extractedProperties = Utils.getConfigs("webservice.properties");
            ConfiguracionUtilValues.LOG_FILE_PATH = extractedProperties.getProperty("LOG_FILE_PATH");
            ConfiguracionUtilValues.TIME_OUT = extractedProperties.getProperty("TIME_OUT");
            ConfiguracionUtilValues.KEY = extractedProperties.getProperty("KEY");           
            WsApplication.registrarInfoLog("Se asignó correctamente las varibles desde el archivo de propiedades");
        } catch (FileNotFoundException e1) {
            WsApplication.registrarErrorLog(e1.toString());
        } catch (IOException e1) {
             WsApplication.registrarErrorLog(e1.toString());
        }
    }
}


