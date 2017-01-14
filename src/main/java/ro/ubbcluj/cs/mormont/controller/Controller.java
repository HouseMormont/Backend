package ro.ubbcluj.cs.mormont.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.ubbcluj.cs.mormont.Service;
import ro.ubbcluj.cs.mormont.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static ro.ubbcluj.cs.mormont.entity.Headers.PASSWORD;
import static ro.ubbcluj.cs.mormont.entity.Headers.USERNAME;
import static ro.ubbcluj.cs.mormont.utils.InputsUtil.getRequiredHeader;
import static ro.ubbcluj.cs.mormont.utils.OutputsUtil.*;


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
    private static final String CREATE_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/create";
    private static final String GET_ALL_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/getAllDocuments";
    private static final String DELETE_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/delete";
    private static final String GET_ALL_DOCUMENTS = "/getAllDocuments";
    private static final String APPROVE_DOC = "/approveDoc";
    private static final String REJECT_DOC = "/rejectDoc";
    private static final String REVISE_DOC = "/reviseDoc";
    private static final String AUTHORIZATION = "/login";
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Service mService;

    public enum DOCUMENTS_TYPE{
        DISPOZITIA_RECTORULUI,
        REFERAT_NECESITATE,
    }

    @Autowired
    public Controller(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        mService = new Service();
    }

    @RequestMapping(value = TEST_LOGIN, method = GET)
    public ResponseEntity<String> testLogin(Authentication auth) {
        try {
            if(auth==null) {
                throw new Exception("Not authenticated");
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = SAVE_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> saveDispozitiaRectorului(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");
            String jsonDocument = request.getParameter("jsonDoc");

            mService.updateDocument(username, jsonDocument, Float.parseFloat(versionDoc), Integer.parseInt(idDoc));

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to save the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = CREATE_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> createDispozitiaRectorului(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            mService.createNewDocument(username, json.get("jsonDoc").toString());

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllDispozitiaRectorului(Authentication auth) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            return new ResponseEntity<>(mService.getAllDocumetsForList(username, DOCUMENTS_TYPE.DISPOZITIA_RECTORULUI), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed get all documents:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_DOCUMENTS, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllDocumentsForUser(Authentication auth){
        try{
            if(auth == null){
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();
            return new ResponseEntity<>(mService.getAllDocumetsForList(username, null), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = DELETE_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> deleteDispozitiaRectorului(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");


            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = APPROVE_DOC, produces = "application/json", method = POST)
    public ResponseEntity<String> approveDocument(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");

            mService.approveDocument(username, Integer.parseInt(idDoc), Float.parseFloat(versionDoc));

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = REVISE_DOC, produces = "application/json", method = POST)
    public ResponseEntity<String> reviseDocument(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");


            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = REJECT_DOC, produces = "application/json", method = POST)
    public ResponseEntity<String> rejectDocument(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");


            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

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
