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
import org.springframework.web.context.request.RequestContextHolder;
import ro.ubbcluj.cs.mormont.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ro.ubbcluj.cs.mormont.entity.Headers.PASSWORD;
import static ro.ubbcluj.cs.mormont.entity.Headers.USERNAME;
import static ro.ubbcluj.cs.mormont.utils.InputsUtil.getRequiredHeader;
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
    private static final String AUTHORIZATION = "/login";
    public static final String AUTH_COOKIE = "AUTH_COOKIE";
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

    @RequestMapping(value = AUTHORIZATION, produces = "application/json", method = POST)
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse httpServletResponse) {
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
            //FIXME we should avoid this..
            httpServletResponse.setHeader(AUTH_COOKIE, RequestContextHolder.currentRequestAttributes().getSessionId());
            httpServletResponse.setHeader("Access-Control-Allow-Headers", AUTH_COOKIE);

            return new ResponseEntity<>(response.toString(), OK);

        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed try of user authentication. Exception is:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), UNAUTHORIZED);
        }
    }
}
