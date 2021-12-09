package com.sophos.ws.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuarioxroles",catalog = "kanban", schema = "public")
public class Usuarioxrol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long idusuarioxrol;
    
    @JoinColumn(name = "idrol", referencedColumnName = "idrol")
    @ManyToOne
    private Rol rol;        
    
    
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    
    
    @ManyToOne
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
