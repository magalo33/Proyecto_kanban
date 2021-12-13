package com.sophos.ws.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tareas", catalog = "kanban", schema = "public")
public class Tarea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long idtarea;
    @Column(length = 100)
    private String descripcion;
    @OneToMany(mappedBy = "idtarea")
    private List<Comentariosportarea> comentariosportareasList;
    @JoinColumn(name = "idestado", referencedColumnName = "idestado")
    @ManyToOne
    private Estado estado;

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /*@JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario usuario;*/
    private Long idusuario;

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Tarea() {
    }

    public Tarea(Long idtarea) {
        this.idtarea = idtarea;
    }

    public Long getIdtarea() {
        return idtarea;
    }

    public void setIdtarea(Long idtarea) {
        this.idtarea = idtarea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Comentariosportarea> getComentariosportareasList() {
        return comentariosportareasList;
    }

    public void setComentariosportareasList(List<Comentariosportarea> comentariosportareasList) {
        this.comentariosportareasList = comentariosportareasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtarea != null ? idtarea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tarea)) {
            return false;
        }
        Tarea other = (Tarea) object;
        if ((this.idtarea == null && other.idtarea != null) || (this.idtarea != null && !this.idtarea.equals(other.idtarea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tareas[ idtarea=" + idtarea + " ]";
    }

}
