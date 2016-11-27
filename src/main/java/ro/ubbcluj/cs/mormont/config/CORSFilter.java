package ro.ubbcluj.cs.mormont.config;


import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lucian Bredean on 11/23/2016.
 */
public class CORSFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger(CORSFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getHeader("Origin") != null) {
            String originHeader = request.getHeader("Origin");
            response.addHeader("Access-Control-Allow-Origin", originHeader);
            response.addHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            response.addHeader("Access-Control-Max-Age", "1800");
        }

        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.setStatus(200);
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException var7) {
                LOGGER.log(Level.ALL, "Could not write custom response to OPTIONS request." + var7.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}