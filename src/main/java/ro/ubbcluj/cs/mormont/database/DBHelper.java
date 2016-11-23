package ro.ubbcluj.cs.mormont.database;

import org.springframework.jdbc.core.JdbcTemplate;
import ro.ubbcluj.cs.mormont.Domain.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iusti on 15/11/2016.
 */
public class DBHelper {

    private static JdbcTemplate jdbcTemplate;


    String sql = "INSERT INTO mormont.users (username, password) VALUES (?,?)";


    public DBHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void fillUsers() {

        for (int i = 1; i < 10; i++) {
            addUser("user" + i, "pass" + i);
        }
    }

    public static User findUser(String username) {

        List<User> users = jdbcTemplate.query("SELECT id,username, password FROM mormont.users WHERE username = ?", new Object[]{username},
                (rs, rowNumber) -> new User(rs.getInt("id"), rs.getString("password"), rs.getString("password")));

        return users.size() == 0 ? null : users.get(0);
    }

    public static List<User> findUsers(String category, String atribute) {

        List<User> users = jdbcTemplate.query("SELECT id, username , password FROM mormont.users WHERE ? = ?", new Object[]{category, atribute},
                (rs, rowNumber) -> new User(rs.getInt("id"), rs.getString("password"), rs.getString("password")));

        return users.size() == 0 ? null : users;
    }

    public static void addUser(String username, String password) {
        String sql = "INSERT INTO mormont.users (username, password) VALUES (?,?)";
        jdbcTemplate.update(sql, new Object[]{username, password});

    }
}
