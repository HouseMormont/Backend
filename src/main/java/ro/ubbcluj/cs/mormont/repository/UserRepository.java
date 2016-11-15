package ro.ubbcluj.cs.mormont.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.cs.mormont.database.DBHelper;

/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private DBHelper dbHelper;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        dbHelper=new DBHelper(jdbcTemplate);


        System.out.print(DBHelper.findUsers("username","admin"));

    }

    //TODO implement me
}

