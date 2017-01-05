package ro.ubbcluj.cs.mormont.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ro.ubbcluj.cs.mormont.entity.Headers;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;

/**
 * ****************************************
 * Created by Tirla Alin on 3/11/2016.  *
 * ****************************************
 */
public class InputsUtil {
    @NotNull
    @Contract("_,_->!null")
    public static String getRequiredHeader(HttpServletRequest request, Headers header) throws IllegalArgumentException {
        String headerValue = request.getHeader(header.getValue());
        if (headerValue == null) {
            throw new IllegalArgumentException(format("Required header %s is not specified.", header.getValue()));
        }
        return headerValue;
    }

    @NotNull
    @Contract("_,_->!null")
    public static String getRequiredJsonElementFromBody(String jsonParamBody, String s) throws IllegalArgumentException {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(jsonParamBody).getAsJsonObject();
        String result = o.get(s).toString();
        if (result == null) {
            throw new IllegalArgumentException(format("Required parameter %s not found for this method.", s));
        }
        return result;
    }

    @NotNull
    @Contract("_,_->!null")
    public static String getOptionalHeader(HttpServletRequest request, Headers header) {
        String headerValue = request.getHeader(header.getValue());
        if (headerValue == null) {
            return "";
        }
        return headerValue;
    }
}
