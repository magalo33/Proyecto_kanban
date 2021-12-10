package javaapplication3;

import java.io.Serializable;


public class Usuarioxrol implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idusuarioxrol;
    
    private Rol rol;        
    
    
    
    
    private Usuario idusuario;
   /*
    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }
    */
    

    public Usuarioxrol() {
    }

    public Usuarioxrol(Long idusuarioxrol) {
        this.idusuarioxrol = idusuarioxrol;
    }

    public Long getIdusuarioxrol() {
        return idusuarioxrol;
    }

    public void setIdusuarioxrol(Long idusuarioxrol) {
        this.idusuarioxrol = idusuarioxrol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuarioxrol != null ? idusuarioxrol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarioxrol)) {
            return false;
        }
        Usuarioxrol other = (Usuarioxrol) object;
        if ((this.idusuarioxrol == null && other.idusuarioxrol != null) || (this.idusuarioxrol != null && !this.idusuarioxrol.equals(other.idusuarioxrol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarioxroles[ idusuarioxrol=" + idusuarioxrol + " ]";
    }
    
}
