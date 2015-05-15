/*
 * Copyright 2009-2012 Prime Teknoloji.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.shashki.server.component.chat;

import ru.shashki.server.entity.Shashist;
import ru.shashki.server.service.ShashistService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ChatUsers implements Serializable {

    private List<Shashist> users = new ArrayList<>();

    @Inject
    private ShashistService shashistService;

    public ChatUsers() {
    }

    @PostConstruct
    public void init() {
        users.addAll(shashistService.findAll());
    }

    public List<Shashist> getUsers() {
        return users;
    }

    public void setUsers(List<Shashist> users) {
        this.users = users;
    }

    public void addUser(Shashist user) {
        users.add(user);
    }

    public void removeUser(Shashist user) {
        users.remove(user);
    }

    public void removeUser(String username) {
        Shashist remove = users.stream().filter(shashist -> username.equals(shashist.getPlayerName()))
                .findFirst().get();
        users.remove(remove);
    }

    public boolean contains(Shashist user) {
        return users.contains(user);
    }
}
