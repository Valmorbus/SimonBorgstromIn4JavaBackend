/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nackademin.simonborgstromin4javabackend.facades;

import com.nackademin.simonborgstromin4javabackend.entities.Betyg;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author borgs_000
 */
@Stateless
public class BetygFacade extends AbstractFacade<Betyg> {

    @PersistenceContext(unitName = "pu")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BetygFacade() {
        super(Betyg.class);
    }
    
}
