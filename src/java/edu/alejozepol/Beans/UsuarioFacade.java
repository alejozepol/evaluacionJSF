/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.alejozepol.Beans;

import edu.alejozepol.entidades.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alejo
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "evaluacionJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario login(int documento, String clave) {
        TypedQuery<Usuario> query
                = getEntityManager().createQuery(
                        "SELECT u FROM Usuario u WHERE u.documento = :doc AND u.clave = :clv", Usuario.class);
        query.setParameter("doc", documento);
        query.setParameter("clv", clave);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
