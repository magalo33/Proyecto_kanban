package javaapplication3;

import java.io.Serializable;


public class Rol implements Serializable {

    private static long serialVersionUID = 1L;
    private Long idrol;
    private String descripcion;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the idrol
     */
    public Long getIdrol() {
        return idrol;
    }

    /**
     * @param idrol the idrol to set
     */
    public void setIdrol(Long idrol) {
        this.idrol = idrol;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
