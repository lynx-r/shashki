package ru.shashki.server.config;

import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.03.15
 * Time: 18:43
 */
@Singleton
@ApplicationScoped
public class Config {

    private String vkRedirectUri;
    private String vkClientId;
    private String vkClientSecret;
    private String vkAuthUri;
    private String vkTokenUri;
    private String vkProtectedUri;

    public Config() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("SocialConfig");

        vkClientId = resourceBundle.getString("vk_client_id");
        vkClientSecret = resourceBundle.getString("vk_client_secret");
        vkRedirectUri = resourceBundle.getString("vk_redirect_uri");
        vkAuthUri = resourceBundle.getString("vk_auth_uri");
        vkTokenUri = resourceBundle.getString("vk_token_uri");
        vkProtectedUri = resourceBundle.getString("vk_protected_uri");
    }

    public String getVkRedirectUri() {
        return vkRedirectUri;
    }

    public String getVkClientId() {
        return vkClientId;
    }
    public String getVkClientSecret() {
        return vkClientSecret;
    }

    public String getVkAuthUri() {
        return vkAuthUri;
    }

    public String getVkTokenUri() {
        return vkTokenUri;
    }

    public String getVkProtectedUri() {
        return vkProtectedUri;
    }
}
