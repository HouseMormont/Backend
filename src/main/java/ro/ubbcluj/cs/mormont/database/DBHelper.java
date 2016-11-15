package ro.ubbcluj.cs.mormont.database;

import org.springframework.jdbc.core.JdbcTemplate;
import ro.ubbcluj.cs.mormont.Domain.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * Created by Iusti on 15/11/2016.
 */
public class DBHelper {

    private static JdbcTemplate jdbcTemplate;


    String sql = "INSERT INTO mormont.users (username, password) VALUES (?,?)";

    public DBHelper(JdbcTemplate jdbcTemplate) {

    }


    public static ArrayList<User> findUsers(String username, String admin) {
        throw new NotImplementedException();
    }
}
