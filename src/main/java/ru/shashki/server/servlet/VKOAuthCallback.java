package ru.shashki.server.servlet;

import org.jboss.logging.Logger;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import ru.shashki.server.config.Config;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
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
 * Time: 10:38
 */
@WebServlet(value = "/vkOAuthCallback", asyncSupported = true)
public class VKOAuthCallback extends HttpServlet {

    @Inject
    private Config config;

    @Inject
    private Logger logger;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Check if the user have rejected
        String error = req.getParameter("error");
        if ((null != error) && ("access_denied" .equals(error.trim()))) {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.sendRedirect(req.getContextPath());
            return;
        }
        //OK the user have consented so lets find out about the user
        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(new GetUserInfo(req, resp, asyncContext));
    }

    private class GetUserInfo implements Runnable {

        private HttpServletRequest req;
        private HttpServletResponse resp;
        private AsyncContext asyncContext;

        public GetUserInfo(HttpServletRequest req, HttpServletResponse resp, AsyncContext asyncContext) {
            this.req = req;
            this.resp = resp;
            this.asyncContext = asyncContext;
        }

        @Override
        public void run() {
            HttpSession session = req.getSession();
            OAuthService service = (OAuthService) session.getAttribute("oauth2Service");
            //Get the all important authorization code
            String code = req.getParameter("code");
            //Construct the access accessToken
            Token accessToken = service.getAccessToken(null, new Verifier(code));
            //Save the accessToken for the duration of the session
            session.setAttribute("accessToken", accessToken);
            try {
                JSONObject json = new JSONObject(accessToken.getRawResponse());
                //Now do something with it - get the user's G+ profile
                OAuthRequest oReq = new OAuthRequest(Verb.GET,
                        String.format(config.getVkProtectedUri(), json.get("user_id")));
//            service.signRequest(accessToken, oReq);
                Response oResp = oReq.send();

                //Read the result
//            JsonReader reader = Json.createReader(new ByteArrayInputStream(
//                    oResp.getBody().getBytes()));
                JSONObject profile = new JSONObject(oResp.getBody());
//            JsonObject profile = reader.readObject();
//            Save the user details somewhere or associate it with
//            session.setAttribute("name", profile.getString("name"));
//            session.setAttribute("email", profile.getString("email"));
                asyncContext.complete();
            } catch (JSONException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
