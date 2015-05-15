package ru.shashki.server.jsf.view;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.05.15
 * Time: 18:37
 */
@ManagedBean
@ApplicationScoped
public class ImageStreamView extends BaseView {

    public StreamedContent getImage(byte[] image) throws IOException {
        return new DefaultStreamedContent(new ByteArrayInputStream(image));
    }
}
