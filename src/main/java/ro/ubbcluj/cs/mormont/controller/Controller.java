package ro.ubbcluj.cs.mormont.controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubbcluj.cs.mormont.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ro.ubbcluj.cs.mormont.entity.Headers.PASSWORD;
import static ro.ubbcluj.cs.mormont.entity.Headers.USERNAME;
import static ro.ubbcluj.cs.mormont.utils.InputsUtil.getRequiredHeader;
import static ro.ubbcluj.cs.mormont.utils.InputsUtil.getRequiredJsonElementFromBody;
import static ro.ubbcluj.cs.mormont.utils.OutputsUtil.getAuthDetails;
import static ro.ubbcluj.cs.mormont.utils.OutputsUtil.getExceptionDetails;


/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@RestController
public class Controller {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private static final String TEST_LOGIN = "/check_login";
    private static final String SAVE_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/save";
    private static final String AUTHORIZATION = "/login";
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public Controller(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    //TODO Remove this function
    @RequestMapping(value = TEST_LOGIN, method = GET)
    public ResponseEntity<String> testLogin(Authentication auth) {
        try {
            if(auth==null) {
                throw new Exception("Not authenticated");
            }
            return new ResponseEntity<>(String.format("Welcome %s", ((User)auth.getPrincipal()).getUsername()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("You are not authenticated", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = SAVE_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> saveDispozitiaRectorului(Authentication auth, HttpServletRequest request) {
        try {
            if(auth==null) {
                return new ResponseEntity<>("You are not authenticated", UNAUTHORIZED);
            }
            String jsonParamBody = request.getParameter("json");

            String username = ((User)auth.getPrincipal()).getUsername();
            String idDoc = getRequiredJsonElementFromBody(jsonParamBody, "idDoc");
            String versionDoc = getRequiredJsonElementFromBody(jsonParamBody, "versionDoc");
            String jsonDocument = getRequiredJsonElementFromBody(jsonParamBody, "jsonDoc");


            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to save the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = AUTHORIZATION, produces = "application/json", method = POST)
    public ResponseEntity<String> login(HttpServletRequest request) {
        //FIXME discuss what we should log and what not because currently we might have too many logs
        try {
            //FIXME plain text credentials!!!!
            //TODO discuss if we need a single header for both?
            String username = getRequiredHeader(request, USERNAME);
            String password = getRequiredHeader(request, PASSWORD);

            LOGGER.log(Level.ALL, "Username {0} tries to authenticate from {1}", new Object[]{username, request.getRemoteAddr()});

            //TODO spring user, maybe we want to extend it and personalize for us???
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(authToken);
            if (authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);

            } else {
                throw new Exception(format("Unable to generate and save token for user %s.", username));
            }
            User user = new User(username, password, authenticate.getAuthorities());

            LOGGER.log(Level.ALL, "Username {0} authenticated with success", new Object[]{username});

            JsonObject response = getAuthDetails(user);
            return new ResponseEntity<>(response.toString(), OK);

        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed try of user authentication. Exception is:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), UNAUTHORIZED);
        }
    }
}
