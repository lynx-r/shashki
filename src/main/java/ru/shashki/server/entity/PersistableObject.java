package ru.shashki.server.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.12.14
 * Time: 10:20
 */
public interface PersistableObject extends Serializable {

    Long getId();

    void setId(Long id);

    Integer getVersion();

    void setVersion(Integer version);
}
