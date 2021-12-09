package com.sophos.ws.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="usuarios",catalog = "kanban", schema = "public")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long idusuario;
    @Column(length = 100)
    private String usuario;
    @Column(length = 100)
    private String password;
    
    
    @OneToMany(mappedBy = "idusuario")
    private List<Usuarioxrol> usuarioxrolesList;
   
    
    @OneToMany(mappedBy = "usuario")
    private List<Tarea> tareasList;

    public Usuario() {
    }

    public Usuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Usuarioxrol> getUsuarioxrolesList() {
        return usuarioxrolesList;
    }

    public void setUsuarioxrolesList(List<Usuarioxrol> usuarioxrolesList) {
        this.usuarioxrolesList = usuarioxrolesList;
    }

    public List<Tarea> getTareasList() {
        return tareasList;
    }

    public void setTareasList(List<Tarea> tareasList) {
        this.tareasList = tareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarios[ idusuario=" + idusuario + " ]";
    }
    
}
