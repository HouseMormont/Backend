package ro.ubbcluj.cs.mormont.controller;

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
import static ro.ubbcluj.cs.mormont.entity.Responses.MESSAGE;
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
    private static final String SAVE_REFERAT_NECESITATE = "/referatNecesitate/save";
    private static final String CREATE_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/create";
    private static final String CREATE_REFERAT_NECESITATE = "/referatNecesitate/create";
    private static final String GET_ALL_DISPOZITIA_RECTORULUI = "/dispozitiaRectorului/getAllDocuments";
    private static final String GET_ALL_REFERAT_NECESITATE = "/referatNecesitate/getAllDocuments";
    private static final String DELETE_DISPOZITIA_RECTORULUI = "/delete";
    private static final String GET_DOCUMENT_BY_ID = "/getDocumentById";
    private static final String GET_ALL_DOCUMENTS = "/getAllDocuments";
    private static final String APPROVE_DOC = "/approveDoc";
    private static final String REJECT_DOC = "/rejectDoc";
    private static final String REVISE_DOC = "/reviseDoc";
    private static final String AUTHORIZATION = "/login";
    private static final String INVALIDATE_SESSION = "/invalidate";
    private static final String FINALIZARE = "/finalizare";
    private static final String DOCS_TO_REVIEW = "/getDocumentsToReview";
    private static final String DOWNLOAD = "/downloadPdf";

    private static final String CREATE_USER = "/createUser";
    private static final String DELETE_USER = "/deleteUser";
    private static final String GET_ALL_USERS = "/getAllUsers";
    private static final String GET_ALL_AUTHORITIES = "/getAuthorities";
    private static final String GET_ALL_FUNCTIONS = "/getFunctions";
    private static final String GET_ALL_TYPES = "/getTypes";


    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Service mService;

    public enum DOCUMENTS_TYPE {
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
            if (auth == null) {
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

            mService.updateDocument(username, jsonDocument, Float.parseFloat(versionDoc), Integer.parseInt(idDoc), "DR");

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to save the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = SAVE_REFERAT_NECESITATE, produces = "application/json", method = POST)
    public ResponseEntity<String> saveReferatNecesitate(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");
            String jsonDocument = request.getParameter("jsonDoc");

            mService.updateDocument(username, jsonDocument, Float.parseFloat(versionDoc), Integer.parseInt(idDoc), "RN");

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to save the necesity report:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_DOCUMENT_BY_ID, produces = "application/json", method = POST)
    public ResponseEntity<String> getDocumentById(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String idDoc = json.getAsString("idDoc");
            String versionDoc = json.getAsString("verDoc");
            String docType = json.getAsString("docType");

            return new ResponseEntity<>(mService.getDocumentById(username, Float.parseFloat(versionDoc), Integer.parseInt(idDoc),docType), OK);

            // TODO populate this json with the response

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

            mService.createNewDocument(username, json.get("jsonDoc").toString(), "DR");

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = CREATE_REFERAT_NECESITATE, produces = "application/json", method = POST)
    public ResponseEntity<String> createReferatNeceistate(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            mService.createNewDocument(username, json.get("jsonDoc").toString(), "RN");

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create the necesity report:", exception);

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

    @RequestMapping(value = GET_ALL_REFERAT_NECESITATE, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllReferatNecesitate(Authentication auth) {
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
    public ResponseEntity<String> getAllDocumentsForUser(Authentication auth) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();
            return new ResponseEntity<>(mService.getAllDocumetsForList(username, null), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all documents the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = DOCS_TO_REVIEW, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllDocumentsToReview(Authentication auth) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();
            return new ResponseEntity<>(mService.getAllDocumetsForReviewForList(username), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all documents the rector disposition:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = DELETE_DISPOZITIA_RECTORULUI, produces = "application/json", method = POST)
    public ResponseEntity<String> deleteDispozitiaRectorului(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String idDoc = json.getAsString("idDoc");
            String versionDoc = json.getAsString("verDoc");
            String docType = json.getAsString("docType");

            mService.removeDocument(idDoc, versionDoc, docType);

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to delete the rector disposition:", exception);

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

            mService.approveDocument(username, Integer.parseInt(idDoc), Float.parseFloat(versionDoc), "DR");

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

    @RequestMapping(value = FINALIZARE, produces = "application/json", method = POST)
    public ResponseEntity<String> startDocumentFlow(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            String username = ((User) auth.getPrincipal()).getUsername();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String idDoc = json.getAsString("idDoc");
            String versionDoc = json.getAsString("verDoc");
            String docType = json.getAsString("docType");
            mService.startDocumentFlow(Integer.parseInt(idDoc), Float.parseFloat(versionDoc), username, docType);

            // TODO populate this json with the response
            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to finalize doc:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_AUTHORITIES, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllAuthorities(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            return new ResponseEntity<>(mService.getAllAuthorities(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all authorities:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_FUNCTIONS, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllFunctions(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            return new ResponseEntity<>(mService.getAllFunctions(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all functions:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_TYPES, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllTypes(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            return new ResponseEntity<>(mService.getAllTypes(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all types:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = CREATE_USER, produces = "application/json", method = POST)
    public ResponseEntity<String> createUser(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String username = json.getAsString("username");
            String password = json.getAsString("password");
            String firstName = json.getAsString("firstName");
            String lastName = json.getAsString("lastName");
            int authority = (int)json.getAsNumber("authority");
            int functie = (int)json.getAsNumber("functie");
            int type = (int)json.getAsNumber("type");

            mService.createUser(username, password, firstName, lastName, authority, functie, type);

            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to create user:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = DELETE_USER, produces = "application/json", method = POST)
    public ResponseEntity<String> deleteUser(@RequestBody String body, Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(body);

            // idDoc/versionDoc/jsonDocument are null if they are not passed as parameters in the request
            String username = json.getAsString("username");


            mService.deleteUsername(username);

            JsonObject response = new JsonObject();

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to delete user:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = GET_ALL_USERS, produces = "application/json", method = POST)
    public ResponseEntity<String> getAllUsers(Authentication auth, HttpServletRequest request) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }

            return new ResponseEntity<>(mService.getAllUsers(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to get all users:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }
    @RequestMapping(value = INVALIDATE_SESSION, produces = "application/json", method = POST)
    public ResponseEntity<String> invalidateSession(Authentication auth) {
        try {
            if (auth == null) {
                return getUnauthorizedResponse();
            }
            SecurityContextHolder.getContext().setAuthentication(null);
            JsonObject response = new JsonObject();
            response.addProperty(MESSAGE.getValue(), "Success");

            return new ResponseEntity<>(response.toString(), OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to invalidate session:", exception);

            JsonObject response = getExceptionDetails(exception);
            return new ResponseEntity<>(response.toString(), BAD_REQUEST);
        }
    }

    @RequestMapping(value = DOWNLOAD, produces = "application/pdf", method = GET)
    public ResponseEntity<byte[]> downloadDoc(HttpServletRequest request, Authentication auth) {
        try {
            String keyStoreLocation = "keystore.p12";
            String keyStorePassword = "changeit";
            String keyStoreType = "PKCS12";
            if (auth == null) {
                return new ResponseEntity<>(UNAUTHORIZED);
            }
            String username = ((User) auth.getPrincipal()).getUsername();

            String idDoc = request.getParameter("idDoc");
            String versionDoc = request.getParameter("versionDoc");
            String docType = request.getParameter("docType");

            //String doc = "HARDCODED VALUE";
            String doc = mService.getDocumentById(username, Float.parseFloat(versionDoc), Integer.parseInt(idDoc), docType);

            byte[] docAsPdf = mService.getDocumentAsPdf(doc, keyStoreLocation, keyStorePassword, keyStoreType);

            return new ResponseEntity<>(docAsPdf, OK);
        } catch (Exception exception) {
            LOGGER.log(SEVERE, "Failed to download document:", exception);

            return new ResponseEntity<>(BAD_REQUEST);
        }
    }
}
