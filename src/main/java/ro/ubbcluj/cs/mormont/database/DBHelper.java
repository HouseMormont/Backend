package ro.ubbcluj.cs.mormont.database;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import ro.ubbcluj.cs.mormont.Domain.User;

/**
 * Created by Iusti on 15/11/2016.
 */
public class DBHelper {

    private static JdbcTemplate jdbcTemplate;

    }

        String sql = "INSERT INTO mormont.users (username, password) VALUES (?,?)";

    }
}
