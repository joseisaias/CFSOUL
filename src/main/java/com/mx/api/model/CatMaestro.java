package com.mx.api.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author abel_
 */
@Entity
@Table(name = "cat_maestro")
@Getter
@Setter
public class CatMaestro extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat_maestro")
    private Integer idCatMaestro;
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "clave")
    private String clave;

    public CatMaestro() {
    }

    public CatMaestro(Integer idCatMaestro) {
        this.idCatMaestro = idCatMaestro;
    }

    public CatMaestro(Integer idCatMaestro, String descripcion, String clave, Date fechaAlta) {
        this.idCatMaestro = idCatMaestro;
        this.descripcion = descripcion;
        this.clave = clave;
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatMaestro != null ? idCatMaestro.hashCode() : 0);
        return hash;
    }

}
