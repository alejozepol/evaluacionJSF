package edu.alejozepol.entidades;

import edu.alejozepol.entidades.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.3.v20160914-rNA", date="2017-03-07T01:27:28")
@StaticMetamodel(Tipodocumento.class)
public class Tipodocumento_ { 

    public static volatile SingularAttribute<Tipodocumento, String> tipo;
    public static volatile SingularAttribute<Tipodocumento, String> sigla;
    public static volatile ListAttribute<Tipodocumento, Usuario> usuarioList;
    public static volatile SingularAttribute<Tipodocumento, Integer> id;

}