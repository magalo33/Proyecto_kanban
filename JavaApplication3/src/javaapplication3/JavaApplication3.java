/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import com.google.gson.Gson;

/**
 *
 * @author Magalo
 */
public class JavaApplication3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       String dato = "{\"usuarioRequest\":{\"usuario\":{\"usuario\":\"otro_usuario__2_garzonq@gmail.com\",\"password\":\"49292684ca6a77398e35c7c738ec125a\"}},\"expdate\":\"2021-12-09 22:25:38\"}";
       Gson gson = new Gson();
       UsuarioBaseDto ub = gson.fromJson(dato, UsuarioBaseDto.class);
        System.out.println(ub);
        
       
    }
    
}
