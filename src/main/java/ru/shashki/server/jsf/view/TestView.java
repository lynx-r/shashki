package ru.shashki.server.jsf.view;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.05.15
 * Time: 8:19
 */
@ManagedBean
@ViewScoped
public class TestView {

    public StreamedContent getImg() {
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap());
        return new DefaultStreamedContent();
    }
}
