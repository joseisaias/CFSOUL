package com.mx.api.dto;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private Integer idEstatusUsuario;
    private Integer idPersona;
    private String usuario;
    private String fechaAlta;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String rfc;
    private String curp;
    private String email;
    private String telefono;
    private Integer estatusPersona;
    private String nombreCompleto;
    private String indStatusString;
    private Integer idRol;

}
