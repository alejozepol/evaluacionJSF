package edu.alejozepol.entidades;

import edu.alejozepol.entidades.Permiso;
import edu.alejozepol.entidades.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.3.v20160914-rNA", date="2017-03-07T01:27:28")
@StaticMetamodel(Permiso.class)
public class Permiso_ { 

    public static volatile SingularAttribute<Permiso, Permiso> permisoPadre;
    public static volatile ListAttribute<Permiso, Permiso> subPermisos;
    public static volatile ListAttribute<Permiso, Rol> roles;
    public static volatile SingularAttribute<Permiso, String> icon;
    public static volatile SingularAttribute<Permiso, Integer> id;
    public static volatile SingularAttribute<Permiso, String> nombre;
    public static volatile SingularAttribute<Permiso, String> url;

}