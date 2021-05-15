package com.buyathome.backend.models.entity;




import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @Column(unique = true)
    @NotBlank
    @Email(message = "El correo electrónico debe ser válido")
    private String correo;

    @Size(min = 8, message="La contraseña debe tener 8 caracteres como minimo")
    private String password;

    private Boolean estado;

    @NotBlank(message="Dato obligatorio")
    @Pattern(regexp = "[a-zA-Z]+", message="Este campo solo puede contener letras")
    private String nombres;

    @NotBlank(message="Dato obligatorio")
    @Pattern(regexp = "[a-zA-Z]+", message="Este campo solo puede contener letras")
    private String apellidos;

    //@Pattern(regexp="^[0-9]{4}-[0-9]{2}-[0-9]{2}$",message="El formato de la fecha de nacimiento es incorrecto")
    private Date fechaNacimiento;


    @NotBlank(message="Dato obligatorio")
    @Pattern(regexp = "[0-9]+", message="Este campo solo puede contener números")
    @Size(min = 8, message="Este campo debe tener 8 numeros")
    private String telefono;

    @NotBlank(message = "Dato obligatorio")
    private String direccion;

    public Integer getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEstado() {
        return estado;
    }
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    private static final long serialVersionUID = 1L;
}
