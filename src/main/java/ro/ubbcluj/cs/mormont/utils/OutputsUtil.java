package ro.ubbcluj.cs.mormont.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static ro.ubbcluj.cs.mormont.entity.Responses.AUTHORITIES;
import static ro.ubbcluj.cs.mormont.entity.Responses.MESSAGE;

/**
 * ****************************************
 * Created by Tirla Alin on 3/11/2016.  *
 * ****************************************
 */
public class OutputsUtil {
    //TODO discuss format of the response
    @NotNull
    @Contract("_->!null")
    public static JsonObject getAuthDetails(User user) {
        JsonObject result = new JsonObject();
        JsonArray authorities = new JsonArray();
        //TODO only one authority or more?
        user.getAuthorities().forEach(grantedAuthority -> authorities.add(grantedAuthority.toString()));
        result.addProperty(MESSAGE.getValue(), "Success");
        result.add(AUTHORITIES.getValue(), authorities);
        return result;
    }

    public static @NotNull ResponseEntity<String> getUnauthorizedResponse() {
        // TODO should be json
        return new ResponseEntity<>("You are not authenticated", UNAUTHORIZED);
    }

    @Contract("_->!null")
    public static JsonObject getExceptionDetails(Exception exception) {
        JsonObject result = new JsonObject();
        result.addProperty(MESSAGE.getValue(), "Failure");
        return result;
    }
}
