package ru.shashki.server;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "primeBean")
public class PrimeBean implements Serializable {

    private static final long serialVersionUID = -2403138950814741653L;
    private String name;

    public PrimeBean() {
        System.out.println("post construct: initialize");
        name = "John";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
