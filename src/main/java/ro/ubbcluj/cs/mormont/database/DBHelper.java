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
        //selectFacultate(2);
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

    public List<Map<String, Object>> getAllDocumentsForUser(String username){
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple where username = ?";
        return jdbcTemplate.queryForList(sql, username);

    }

    public int generateID(){
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        int maxId = 0;
        for (Map row:rows){
            if((int)row.get("id_dispozitie") > maxId){
                maxId = (int)row.get("id_dispozitie");
            }
        }

        return maxId + 1;

    }

    public int getUserTypeId(String username) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_tip_solicitant";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map row:rows){
            if( row.get("username").toString().equals(username) ){
                return (int)row.get("funcite");
            }
        }

        return -1;
    }

    public void saveNewDocument(int id, float versiune, String username, int tipUser, String date, String document) {
        String sql = "INSERT INTO mormont.Dispozitia_Rectorului_Simple" +
                " (id_dispozitie, versiune, username, tip_initiator, data, documentJson) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{id, versiune, username, tipUser, date, document});

    }

    public void saveNewDocumentVersion(int idDocument, float versiuneNoua, String username, int idTipSolicitant, String document, String date) {
        String sql = "INSERT INTO mormont.Dispozitia_Rectorului_Simple" +
                " (id_dispozitie, versiune, username, tip_initiator, data, documentJson) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{idDocument, versiuneNoua, username, idTipSolicitant, date, document});
    }

    public String getDocumentDate(int id) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple where id = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id);
        return rows.get(0).get("data").toString();
    }

    public String getDocumentJson(int id, float versiune) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple where id = ? and versiune = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{id, versiune});
        return rows.get(0).get("documentJson").toString();
    }

    public List<Map<String, Object>> getFlowForUserType(int userTypeId) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Flux WHERE id_tip_solicitant = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userTypeId);
        return rows;

    }

    public void makeDocumentFinal(int id, float newVersion, int userTypeId, int aprobare, String username, String documentDate, String documentJson) {
        String sql = "INSERT INTO mormont.Dispozitia_Rectorului_Simple" +
                "VALUES (?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{id, newVersion, userTypeId, aprobare,username, documentDate, documentJson });

    }

    public Map<String, Object> getDocument(int id, float versiune){
        String sql = "SELECT * FROM Dispozitia_Rectorului_Simple WHERE id_dispozitie = ? and versiune = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{id, versiune});
        return rows.get(0);
    }

    public void updateDocumentStatus(int id, float versiune, int nextApproval) {
        String sql = "UPDATE mormont.Dispozitia_Rectorului_Simple SET id_aprobare = " +nextApproval + "WHERE id_dispozitie = ? and versiune = ?";

        jdbcTemplate.update(sql, new Object[]{id, versiune});

    }
}
