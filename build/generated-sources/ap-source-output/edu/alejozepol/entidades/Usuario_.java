package edu.alejozepol.entidades;

import edu.alejozepol.entidades.Adjunto;
import edu.alejozepol.entidades.Rol;
import edu.alejozepol.entidades.Tipodocumento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.3.v20160914-rNA", date="2017-03-07T01:27:28")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> segundoNombre;
    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, Integer> estado;
    public static volatile SingularAttribute<Usuario, String> primerNombre;
    public static volatile SingularAttribute<Usuario, String> primerApellido;
    public static volatile ListAttribute<Usuario, Rol> roles;
    public static volatile SingularAttribute<Usuario, Integer> documento;
    public static volatile SingularAttribute<Usuario, String> segundoApellido;
    public static volatile SingularAttribute<Usuario, Tipodocumento> tipoDocumento;
    public static volatile SingularAttribute<Usuario, String> correo;
    public static volatile SingularAttribute<Usuario, Character> tipoUsuario;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile ListAttribute<Usuario, Adjunto> adjuntos;

}