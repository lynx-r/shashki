package ru.shashki.server.dao;

import ru.shashki.server.entity.PersistableObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:58
 */
 public interface Dao<E extends PersistableObject> {
   void create(E entity);

   void edit(E entity);

   void remove(E entity);

   E find(Object id);

   List<E> findAll();

   List<E> findRange(int first, int count);
}
