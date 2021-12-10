/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;




/**
 *
 * @author Magalo
 */

public class UsuarioBaseDto {    

    @Override
    public String toString() {
        return "UsuarioBaseDto{" + "usuarioRequest=" + usuarioRequest + ", expdate=" + expdate + '}';
    }
    
    
    private UsuarioRequestDto usuarioRequest;    
    private String expdate;

    /**
     * @return the usuarioRequest
     */
    public UsuarioRequestDto getUsuarioRequest() {
        return usuarioRequest;
    }

    /**
     * @param usuarioRequest the usuarioRequest to set
     */
    public void setUsuarioRequest(UsuarioRequestDto usuarioRequest) {
        this.usuarioRequest = usuarioRequest;
    }

    /**
     * @return the expdate
     */
    public String getExpdate() {
        return expdate;
    }

    /**
     * @param expdate the expdate to set
     */
    public void setExpdate(String expdate) {
        this.expdate = expdate;
    }
}
