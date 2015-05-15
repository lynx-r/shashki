package ru.shashki.server.servlet;

import org.jboss.logging.Logger;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;
import ru.shashki.server.config.Config;
import ru.shashki.server.entity.AuthProvider;
import ru.shashki.server.entity.Shashist;
import ru.shashki.server.entity.ShashistStatus;
import ru.shashki.server.service.ShashistService;
import ru.shashki.server.util.StreamUtil;

import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

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
    @Inject
    private ShashistService shashistService;

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
                Integer userId = json.getInt("user_id");
                String email = json.getString("email");
                Shashist shashist = shashistService.findByUserId(userId.toString());

                if (shashist != null) {
                    shashist.setVisitCounter(shashist.getVisitCounter() + 1);
                    shashist.setLastVisitedDate(LocalDate.now());
                    shashist.setStatus(ShashistStatus.ONLINE);
                    shashist.setSessionId(session.getId());
                    shashistService.edit(shashist);
                } else {
                    OAuthRequest oReq = new OAuthRequest(Verb.GET,
                            String.format(config.getVkProtectedUri(), userId));
                    Response oResp = oReq.send();

                    JSONObject response = new JSONObject(oResp.getBody());
                    JSONArray array = response.getJSONArray("response");
                    JSONObject profile = array.getJSONObject(0);
                    shashist = new Shashist();
                    shashist.setStatus(ShashistStatus.ONLINE);
                    shashist.setRegisterDate(LocalDate.now());
                    shashist.setAuthProvider(AuthProvider.VK);
                    shashist.setUserId(userId.toString());
                    shashist.setEmail(email);
                    shashist.setFirstName(profile.getString("first_name"));
                    shashist.setLastName(profile.getString("last_name"));
                    shashist.setPlayerName(shashist.getPublicName());
                    shashist.setLastVisitedDate(LocalDate.now());
                    shashist.setVisitCounter(1);
                    shashist.setSessionId(session.getId());
                    shashist.setPhoto(downloadPhoto(profile.getString("photo")));

                    shashistService.create(shashist);
                }

                asyncContext.complete();
                resp.sendRedirect("/64/draughts/play.jsf");
            } catch (JSONException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        private byte[] downloadPhoto(String photo) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) new URL(photo).openConnection();
            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                StreamUtil.copy(urlConnection.getInputStream(), outputStream);
                urlConnection.disconnect();
                return outputStream.toByteArray();
            } else {
                logger.error("No file downloaded");
            }
            urlConnection.disconnect();
            return null;
        }
    }
}
