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
@Table(name="comentariosportareas",catalog = "kanban", schema = "public")
public class Comentariosportarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long idcomentariosportarea;
    @Column(length = 500)
    private String comentario;
    
    
    /*@JoinColumn(name = "idtarea", referencedColumnName = "idtarea")
    @ManyToOne
    private Tarea idtarea;*/
    
    private Long idtarea;
    public Long getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(Long idtarea) {
        this.idtarea = idtarea;
    }

    
    

    public Comentariosportarea() {
    }

    public Comentariosportarea(Long idcomentariosportarea) {
        this.idcomentariosportarea = idcomentariosportarea;
    }

    public Long getIdcomentariosportarea() {
        return idcomentariosportarea;
    }

    public void setIdcomentariosportarea(Long idcomentariosportarea) {
        this.idcomentariosportarea = idcomentariosportarea;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomentariosportarea != null ? idcomentariosportarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentariosportarea)) {
            return false;
        }
        Comentariosportarea other = (Comentariosportarea) object;
        if ((this.idcomentariosportarea == null && other.idcomentariosportarea != null) || (this.idcomentariosportarea != null && !this.idcomentariosportarea.equals(other.idcomentariosportarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Comentariosportareas[ idcomentariosportarea=" + idcomentariosportarea + " ]";
    }
    
}
