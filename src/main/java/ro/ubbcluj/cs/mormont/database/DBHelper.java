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
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple where username = ? UNION SELECT * FROM mormont.referat_necesitate_simple where username = ? ";
        return jdbcTemplate.queryForList(sql, username,username);

    }

    public int generateID(){
        String sql = "SELECT * FROM mormont. referat_necesitate_simple ";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        int maxIdDR = 0;
        for (Map row:rows){
            if((int)row.get("id_dispozitie") > maxIdDR){
                maxIdDR = (int)row.get("id_dispozitie");
            }
        }
        sql = " SELECT * FROM mormont.dispozitia_rectorului_simple";
        int maxIdRN =0;
        rows = jdbcTemplate.queryForList(sql);
        for (Map row:rows){
            if((int)row.get("id_dispozitie") > maxIdRN){
                maxIdRN = (int)row.get("id_dispozitie");
            }
        }
        return ((maxIdDR > maxIdRN) ? maxIdDR  : maxIdRN) + 1;

    }

    public int getUserTypeId(String username) {
        String sql = "SELECT * FROM mormont.Users where username = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, username);

        for (Map row:rows){
            if( row.get("username").toString().equals(username) ){
                return (int)row.get("functie");
            }
        }

        return -1;
    }

    public int getUserAuthority(String username) {
        String sql = "SELECT * FROM mormont.Users where username = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, username);

        for (Map row:rows){
            if( row.get("username").toString().equals(username) ){
                return (int)row.get("authority");
            }
        }

        return -1;
    }

    private String checkDocumentType(String documentType) {
        switch (documentType){
            case("DR"):
                return " mormont.Dispozitia_Rectorului_Simple";
            case ("RN"):
                return " mormont.referat_necesitate_simple";
        }
        return null;
    }

    public void saveNewDocument(int id, float versiune, String username, int tipUser, String date, String document ,String documentType) {
        String sql = "INSERT INTO" +  checkDocumentType(documentType) +
                "(id_dispozitie, versiune, username, tip_initiator, data, documentJson) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{id, versiune, username, tipUser, date, document});

    }




    public void saveNewDocumentVersion(int idDocument, float versiuneNoua, String username, int idTipSolicitant, String document, String date , String documentType) {
        String sql = "INSERT INTO" + checkDocumentType(documentType)
                + " (id_dispozitie, versiune, username, tip_initiator, data, documentJson) VALUES (?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{idDocument, versiuneNoua, username, idTipSolicitant, date, document});

    }


    public String getDocumentDate(int id , String documentType) {
        String sql = "SELECT * FROM" + checkDocumentType(documentType)+" where id_dispozitie = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, id);
        return rows.get(0).get("data").toString();
    }



    public String getDocumentJson(int id, float versiune, String documentType) {
        String sql = "SELECT * FROM "+ checkDocumentType(documentType) +" where id_dispozitie = ? and ROUND(versiune, 1) = ROUND(?, 1)";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{id, versiune});
        return rows.get(0).get("documentJson").toString();
    }


    public List<Map<String, Object>> getFlowForUserType(int userTypeId) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Flux WHERE id_tip_solicitant = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, userTypeId);
        return rows;

    }


    public void makeDocumentFinal(int id, float newVersion, int userTypeId, int aprobare, String username, String documentDate, String documentJson ,String documentType) {
        String sql = "INSERT INTO "  + checkDocumentType(documentType) +
                " (id_dispozitie, versiune, tip_initiator, id_aprobare, username, data, documentJson) VALUES (?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{id, newVersion, userTypeId, aprobare,username, documentDate, documentJson });

    }


    public Map<String, Object> getDocument(int id, float versiune , String documentType){
        String sql = "SELECT * FROM " + checkDocumentType(documentType) + " WHERE id_dispozitie = ? and versiune = ?";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{id, versiune});
        return rows.get(0);
    }



    public List<Map<String, Object>> getAllApprovals(){
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_tip_avizare";
        return jdbcTemplate.queryForList(sql);
    }



    public void updateDocumentStatus(int id, float versiune, int nextApproval, String documentType) {
        String sql = "UPDATE " + checkDocumentType(documentType) +" SET id_aprobare = " +nextApproval + " WHERE id_dispozitie = ? and versiune = ?";

        jdbcTemplate.update(sql, new Object[]{id, versiune});

    }



    public List<Map<String,Object>> getAllDRForUser(String username) {
        String sql = "SELECT * FROM mormont.Dispozitia_Rectorului_Simple where username = ?";
        return jdbcTemplate.queryForList(sql, username);
    }



    public List<Map<String,Object>> getAllRNForUser(String username) {
        String sql = "SELECT * FROM mormont.referat_necesitate_simple where username = ?";
        return jdbcTemplate.queryForList(sql, username);

    }

    public void deleteDocument(String idDoc, String docType) {
        String sql = "DELETE FROM " + checkDocumentType(docType) + " where id_dispozitie = ?";
        jdbcTemplate.update(sql, new Object[]{idDoc});
    }

      public int getOwner(int id, float ver, String docType){
        String sql = "SELECT * FROM " + checkDocumentType(docType) +  " where id_dispozitie = ? and ROUND(versiune, 1) = ROUND(?, 1)";
        List<Map<String,Object>> row = jdbcTemplate.queryForList(sql, new Object[]{id, ver});
        return (int)row.get(0).get("tip_initiator");

    }

    public String getOwnerUsername(int id, float versiune, String docType) {
        String sql = "SELECT * FROM " + checkDocumentType(docType) +  " where id_dispozitie = ? and ROUND(versiune, 1) = ROUND(?, 1)";
        List<Map<String,Object>> row = jdbcTemplate.queryForList(sql, new Object[]{id, versiune});
        return (String )row.get(0).get("username");
    }
}
