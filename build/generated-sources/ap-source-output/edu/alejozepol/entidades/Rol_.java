package edu.alejozepol.entidades;

import edu.alejozepol.entidades.Permiso;
import edu.alejozepol.entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.3.v20160914-rNA", date="2017-03-07T01:27:28")
@StaticMetamodel(Rol.class)
public class Rol_ { 

    public static volatile SingularAttribute<Rol, String> descripcion;
    public static volatile SingularAttribute<Rol, Integer> id;
    public static volatile ListAttribute<Rol, Usuario> usuarios;
    public static volatile ListAttribute<Rol, Permiso> permisos;
    public static volatile SingularAttribute<Rol, String> nombre;

}