package ro.ubbcluj.cs.mormont.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.cs.mormont.entity.DemoUser;

import java.util.List;


/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DemoUser findById(String username) {
        List<DemoUser> users = jdbcTemplate.query("SELECT username, password FROM mormont.users WHERE username = ?", new Object[]{username},
                (rs, rowNumber) -> new DemoUser(rs.getString("username"), rs.getString("password")));
        return users.size() == 0 ? null : users.get(0);
    }
}

