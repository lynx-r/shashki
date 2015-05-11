package ru.shashki.server.jsf.view;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import ru.shashki.server.component.chat.ChatUsers;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
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

    private boolean loggedIn;

    private String privateUser;

    private final static String CHANNEL = "/chat/";

    public ChatView() {
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void sendGlobal() {
        eventBus.publish(CHANNEL + "*", username + ": " + globalMessage);

        globalMessage = null;
    }

    public void sendPrivate() {
        eventBus.publish(CHANNEL + privateUser, "[PM] " + username + ": " + privateMessage);

        privateMessage = null;
    }

    public void login() {
        if (users.contains(username)) {
            loggedIn = false;
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username taken", "Try with another username."));
            getRequestContext().update("growl");
        } else {
            users.addUser(username);
            getRequestContext().execute("PF('subscriber').connect('/" + username + "')");
            loggedIn = true;
        }
    }

    public void disconnect() {
        //remove user and update ui
        users.removeUser(username);
        getRequestContext().update("form:users");

        //push leave information
        eventBus.publish(CHANNEL + "*", username + " left the channel.");

        //reset state
        loggedIn = false;
        username = null;
    }
}
