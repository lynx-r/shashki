package ru.shashki.server.util.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 19.04.15
 * Time: 19:59
 */
public class EntityManagerProducer {

    @Produces
    @PersistenceContext
    private EntityManager em;
}
