package ro.ubbcluj.cs.mormont.database;

import org.springframework.jdbc.core.JdbcTemplate;
import ro.ubbcluj.cs.mormont.Domain.User;
import ro.ubbcluj.cs.mormont.database.tableHelpers.FacultatiHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by Iusti on 15/11/2016.
 */
public class DBHelper {

    private static DBHelper mInstance;
    private static JdbcTemplate jdbcTemplate;


    String sql = "INSERT INTO mormont.users (username, password) VALUES (?,?)";

    public static DBHelper getInstance(JdbcTemplate jdbcTemplate){
        if (mInstance == null){
            mInstance = new DBHelper(jdbcTemplate);
        }
        return mInstance;
    }

    public static DBHelper getInstance(){
        if (mInstance == null){
            throw new IllegalArgumentException("Jdbc not initialized.");
        }
        return mInstance;
    }
    private DBHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        selectFacultate(2);
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


    public static void selectFacultate(int id){
        String sql = "SELECT * FROM mormont.facultati";
//        String res = (String) jdbcTemplate.queryForObject(sql,new Pair<Integer,String>);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(FacultatiHelper.SELECT_BY_ID,id);
        for (Map row:rows){
            System.out.print(row.get("denumire"));
            System.out.print(row.get("id_facultate"));
        }
//        System.out.print(res);
    }
}
