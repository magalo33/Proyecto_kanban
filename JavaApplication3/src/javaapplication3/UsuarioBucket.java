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
public class UsuarioBucket {
    private UsuarioBaseDto usuarioResponseDto;
    private String encriptedData;

    /**
     * @return the usuarioResponseDto
     */
    public UsuarioBaseDto getUsuarioResponseDto() {
        return usuarioResponseDto;
    }

    /**
     * @param usuarioResponseDto the usuarioResponseDto to set
     */
    public void setUsuarioResponseDto(UsuarioBaseDto usuarioResponseDto) {
        this.usuarioResponseDto = usuarioResponseDto;
    }

    /**
     * @return the encriptedData
     */
    public String getEncriptedData() {
        return encriptedData;
    }

    /**
     * @param encriptedData the encriptedData to set
     */
    public void setEncriptedData(String encriptedData) {
        this.encriptedData = encriptedData;
    }
}
