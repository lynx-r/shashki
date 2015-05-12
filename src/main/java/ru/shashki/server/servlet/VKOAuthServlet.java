package ru.shashki.server.servlet;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.oauth.OAuthService;
import ru.shashki.server.config.Config;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 11.05.15
 * Time: 9:46
 */
@WebServlet(value = "/vkOAuthLogin")
public class VKOAuthServlet extends HttpServlet {

    @Inject
    private Config config;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceBuilder builder= new ServiceBuilder();
        OAuthService service = builder.provider(VkontakteApi.class)
                .apiKey(config.getVkClientId())
                .apiSecret(config.getVkClientSecret())
                .callback(config.getVkRedirectUri())
                .scope("email")
                .build();
        HttpSession session = req.getSession();
        session.setAttribute("oauth2Service", service);
        resp.sendRedirect(service.getAuthorizationUrl(null));
    }
}
