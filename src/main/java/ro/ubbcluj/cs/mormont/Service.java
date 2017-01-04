package ro.ubbcluj.cs.mormont;

import com.google.gson.Gson;
import ro.ubbcluj.cs.mormont.database.DBHelper;
import ro.ubbcluj.cs.mormont.entity.DocumentListItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tudorlozba on 04/01/2017.
 */
public class Service {

    public void createNewDocument(String username, String document) {

        int id = DBHelper.getInstance().generateID();
        int idTipSolicitant = DBHelper.getInstance().getUserTypeId(username);
        float versiune = (float) 0.1;
        String date = getDate();

        DBHelper.getInstance().saveNewDocument(id, versiune, username,  idTipSolicitant, date, document);

    }

    public void updateDocument(String username, String document, float versiuneDocument, final int idDocument){
        int idTipSolicitant = DBHelper.getInstance().getUserTypeId(username);
        float versiuneNoua = versiuneDocument + (float)0.1;

        DBHelper.getInstance().saveNewDocumentVersion(idDocument, versiuneNoua, username, idTipSolicitant, document, getDocumentDate(idDocument));
    }


    public void startDocumentFlow(int id, float versiune, String username){
        float newVersion = 1.0f;
        int aprobare = getFirstApprovalNeededForDocument(DBHelper.getInstance().getUserTypeId(username));


        DBHelper.getInstance().makeDocumentFinal(
                id,
                newVersion,
                DBHelper.getInstance().getUserTypeId(username),
                aprobare,
                username,
                getDocumentDate(id),
                getDocumentJson(id, versiune)
                );
    }

    public void approveDocument(String username, int id, float versiune) throws Exception {
        Map<String, Object> document = DBHelper.getInstance().getDocument(id, versiune);
        int initiator = (int)document.get("id_initiator");
        int aprobare = (int)document.get("id_aprobare");

        if(isApprovalFinal(initiator, aprobare)){
            markDocumentFinalized();
        } else {
            int nextApproval = getNextApproval(initiator, aprobare);
            if(nextApproval == -1){
                throw new Exception("Something went wrong! Next Approval not found in the database");
            }
            DBHelper.getInstance().updateDocumentStatus(id, versiune, nextApproval);

        }

    }

    private int getNextApproval(int initiator, int aprobare) {
        List<Map<String, Object>> flow = DBHelper.getInstance().getFlowForUserType(initiator);

        for(Map row: flow){
            if((int)row.get("id_tip_avizare") == aprobare){
                Map nextRow = flow.get(flow.indexOf(row) + 1);
                return (int)nextRow.get("id_tip_avizare");
            }
        }
        return -1;
    }

    private void markDocumentFinalized() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    private boolean isApprovalFinal(int initiator, int aprobare) {
        List<Map<String, Object>> flow = DBHelper.getInstance().getFlowForUserType(initiator);

        for(Map row: flow){
            if((int)row.get("id_tip_avizare") == aprobare && (boolean) row.get("final")){
                return true;
            }
        }

        return false;
    }

    private int getFirstApprovalNeededForDocument(int userTypeId) {
        List<Map<String , Object>> result = DBHelper.getInstance().getFlowForUserType(userTypeId);
        return (int)result.get(0).get("id_tip_avizare");
    }


    public String getDocumentById(String username, float idDocument) {
        List<Map<String, Object>> documents = DBHelper.getInstance().getAllDocumentsForUser(username);
        Gson gson = new Gson();
        for (Map row : documents) {
            if (row.get("id_dispozitie").toString().equals(idDocument))
                return gson.toJson(row);
        }

        return null;
    }

    public String getAllDocumetsForList(String username) {
        List<Map<String, Object>> documents = DBHelper.getInstance().getAllDocumentsForUser(username);
        Gson gson = new Gson();
        ArrayList<DocumentListItem> items = new ArrayList<>();
        for (Map row : documents) {
            DocumentListItem item = new DocumentListItem((int)row.get("id_dispozitie"), (float)row.get("versiune"), (String )row.get("data"));
            items.add(item);
        }

        return gson.toJson(items);
    }

    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }

    private String getDocumentDate(int id){
        return DBHelper.getInstance().getDocumentDate(id);
    }

    private String getDocumentJson(int id, float versiune){
        return DBHelper.getInstance().getDocumentJson(id, versiune);
    }

}
