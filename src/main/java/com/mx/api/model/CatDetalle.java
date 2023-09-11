package com.mx.api.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "cat_detalle")
@Setter
@Getter
public class CatDetalle extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat_detalle")
    private Integer idCatDetalle;
    
    @Column(name = "id_cat_detalle_padre")
    private String idCatDetallePadre;
    
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "clave")
    private String clave;

    @JoinColumn(name = "id_cat_maestro", referencedColumnName = "id_cat_maestro")
    @ManyToOne
    private CatMaestro idCatMaestro;
    

    public CatDetalle() {
    }

    public CatDetalle(Integer id) {
        this.idCatDetalle = id;
    }

    public CatDetalle(Integer idCatDetalle, String descripcion, String clave, Date fechaAlta) {
        this.idCatDetalle = idCatDetalle;
        this.descripcion = descripcion;
        this.clave = clave;
    }
}
