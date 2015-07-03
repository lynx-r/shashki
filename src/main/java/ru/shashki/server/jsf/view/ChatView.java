package ru.shashki.server.jsf.view;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import ru.shashki.server.component.chat.ChatUsers;
import ru.shashki.server.entity.Shashist;
import ru.shashki.server.service.ShashistService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;

@ManagedBean
@SessionScoped
public class ChatView extends BaseView {

    private final EventBus eventBus = EventBusFactory.getDefault().eventBus();

    /*
     * Injecting managed beans in each other
     * http://balusc.blogspot.com/2011/09/communication-in-jsf-20.html#InjectingManagedBeansInEachOther
     */
    @ManagedProperty("#{chatUsers}")
    private ChatUsers users;

    private String privateMessage;

    private String globalMessage;

    private String username;

    private String privateUser;

    private final static String CHANNEL = "/chat/";

    @Inject
    private ShashistService shashistService;

    public ChatView() {
    }

    @PostConstruct
    public void init() {
        System.out.println(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap());
    }

    /*
     * Injecting managed beans in each other
     * http://balusc.blogspot.com/2011/09/communication-in-jsf-20.html#InjectingManagedBeansInEachOther
     */
    public void setUsers(ChatUsers users) {
        this.users = users;
    }

    public String getPrivateUser() {
        return privateUser;
    }

    public void setPrivateUser(String privateUser) {
        this.privateUser = privateUser;
    }

    public String getGlobalMessage() {
        return globalMessage;
    }

    public void setGlobalMessage(String globalMessage) {
        this.globalMessage = globalMessage;
    }

    public String getPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(String privateMessage) {
        this.privateMessage = privateMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void sendGlobal() {
        eventBus.publish(CHANNEL + "*", username + ": " + globalMessage);

        globalMessage = null;
    }

    public void sendPrivate() {
        eventBus.publish(CHANNEL + privateUser, "[PM] " + username + ": " + privateMessage);

        privateMessage = null;
    }

    public StreamedContent getShashistPhoto() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String studentId = context.getExternalContext().getRequestParameterMap().get("shashistId");
            Shashist student = shashistService.find(Long.valueOf(studentId));
            return new DefaultStreamedContent(new ByteArrayInputStream(student.getPhoto()));
        }
    }
}
