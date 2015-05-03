package ru.shashki.server.jsf.view;

import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.05.15
 * Time: 5:24
 */
public class BaseView {

    protected FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }
}
