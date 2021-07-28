package main.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import main.dao.MyDAO;
import main.pojo.Caption;
import main.pojo.UserProgress;
import main.pojo.UserSession;
import org.apache.commons.lang3.StringUtils;
import org.jdbi.v3.core.Jdbi;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.eclipse.jetty.http.HttpCookie.SAME_SITE_NONE_COMMENT;
import static org.eclipse.jetty.http.HttpCookie.SAME_SITE_STRICT_COMMENT;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {
    private final long oneWeekTime = 7 * 24 * 3600 * 1000;
    private static Jdbi jdbi;
    private Map<String, UserSession> session = new HashMap<>();
    ObjectMapper objectMapper = new ObjectMapper();

    public MyResource(Jdbi jdbi) {
        // singleton pattern
        if (this.jdbi == null) {
            this.jdbi = jdbi;
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(ObjectNode requestBody, @CookieParam("sid") Cookie cookie) {
        MyDAO dao = jdbi.onDemand(MyDAO.class);

        // login using password
        String username = Optional.of(requestBody).map(x -> x.get("username")).map(JsonNode::textValue).orElse(null);
        String password = Optional.of(requestBody).map(x -> x.get("password")).map(JsonNode::textValue).orElse(null);

        //username and password input validation
        String regex = "\\w{1,6}$";
        Pattern p = Pattern.compile(regex);

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || !p.matcher(username).matches() || !p.matcher(password).matches()) {
            return Response.status(400).entity(objectMapper.createObjectNode().put("error", "INPUT_INVALID")).build();
        }

        if (dao.getAccount(username) == null) {
            dao.createAccount(username, password);
        } else if (dao.getAccount(username, password) == null) {
            return Response.status(403).entity(objectMapper.createObjectNode().put("error", "PASSWORD_INVALID")).build();
        }

        // save in session
        String sid = UUID.randomUUID().toString();
        session.put(sid, new UserSession(username, System.currentTimeMillis()));

        return Response.ok()
                .entity(objectMapper.createObjectNode().put("sid", sid))
                .build();
    }

    @GET
    @Path("/caption")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response caption(@QueryParam("id") Integer id, @QueryParam("se") String se, @QueryParam("sid") String sid) {
        MyDAO dao = jdbi.onDemand(MyDAO.class);

        // get user from session
        UserSession userSession = Optional.ofNullable(sid).map(session::get).orElse(null);
        if (userSession == null) {
            return Response.status(403).entity(objectMapper.createObjectNode().put("error", "LOGIN_REQUIRED")).build();
        }

        // remove expired session
        session.entrySet().removeIf(x -> (System.currentTimeMillis() - x.getValue().getTime() > oneWeekTime));

        // get caption
        Caption caption = null;
        if (id != null) {
            caption = dao.getCaption(id);
        } else if (se != null) {
            caption = dao.getCaption(se);
        } else {
            UserProgress userProgress = dao.getProgress(userSession.getUsername());
            if (userProgress == null) {
                userProgress = new UserProgress(0, userSession.getUsername(), 10);
            }
            caption = dao.getCaption(userProgress.getLast_pos());
        }

        // update progress
        if (caption != null) {
            dao.updateProgress(userSession.getUsername(), caption.getId());
            return Response.ok().entity(caption).build();

            return Response.status(400).entity(objectMapper.createObjectNode().put("error", "CAPTION_ID_INVALID")).build();
        }
    }
}
