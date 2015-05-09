package ru.shashki.server.component.chat;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 08.05.15
 * Time: 13:30
 */
@ManagedBean
@ApplicationScoped
public class ChatUsers implements Serializable {

    private List<String> users;

    @PostConstruct
    public void init() {
        users = new ArrayList<>();
    }

    public List<String> getUsers() {
        return users;
    }

    public void remove(String user) {
        this.users.remove(user);
    }

    public void add(String user) {
        this.users.add(user);
    }

    public boolean contains(String user) {
        return this.users.contains(user);
    }
}
