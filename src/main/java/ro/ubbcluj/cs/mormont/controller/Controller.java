package ro.ubbcluj.cs.mormont.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubbcluj.cs.mormont.repository.UserRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@RestController
public class Controller {
    private static final String DEMO_API = "/demo";

    private UserRepository userRepository;

    @Autowired
    public Controller(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO Remove this function
    @RequestMapping(value = DEMO_API, method = GET)
    public ResponseEntity<String> demoFunction() {
        try {
            String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

            return new ResponseEntity<>(String.format("Welcome %s", username), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>("You are not authenticated", HttpStatus.NOT_FOUND);
        }
    }
}
