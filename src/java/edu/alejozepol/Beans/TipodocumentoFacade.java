/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.alejozepol.Beans;

import edu.alejozepol.entidades.Tipodocumento;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alejo
 */
@Stateless
public class TipodocumentoFacade extends AbstractFacade<Tipodocumento> {

    @PersistenceContext(unitName = "evaluacionJSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipodocumentoFacade() {
        super(Tipodocumento.class);
    }
    
}
