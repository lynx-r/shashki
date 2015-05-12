package ru.shashki.server.dao;


import ru.shashki.server.entity.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:18
 */
public interface ShashistDao extends Dao<Shashist> {

  Shashist findByUserId(String userId);

  Shashist findBySessionId(String sessionId);
}
