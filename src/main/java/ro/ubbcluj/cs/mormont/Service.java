package ro.ubbcluj.cs.mormont;

import com.google.gson.Gson;
import ro.ubbcluj.cs.mormont.controller.Controller;
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

    public void createNewDocument(String username, String document, String documentType) {

        int id = DBHelper.getInstance().generateID();
        int idTipSolicitant = DBHelper.getInstance().getUserTypeId(username);
        float versiune = (float) 0.1;
        String date = getDate();
        DBHelper.getInstance().saveNewDocument(id, versiune, username, idTipSolicitant, date, document, documentType);
    }

    public void updateDocument(String username, String document, float versiuneDocument, final int idDocument, String documentType) {
        int idTipSolicitant = DBHelper.getInstance().getUserTypeId(username);
        float versiuneNoua = versiuneDocument + (float) 0.1;

        DBHelper.getInstance().saveNewDocumentVersion(idDocument, versiuneNoua, username, idTipSolicitant, document, getDocumentDate(idDocument, documentType), documentType);
    }


    public void startDocumentFlow(int id, float versiune, String username, String documentType) {
        float newVersion = (float) 1.0;
        int aprobare = getFirstApprovalNeededForDocument(DBHelper.getInstance().getUserTypeId(username));


        DBHelper.getInstance().makeDocumentFinal(
                id,
                newVersion,
                DBHelper.getInstance().getOwner(id, versiune, documentType),
                aprobare,
                DBHelper.getInstance().getOwnerUsername(id, versiune, documentType),
                getDocumentDate(id, documentType),
                getDocumentJson(id, versiune, documentType),
                documentType
        );
    }

    public void approveDocument(String username, int id, float versiune, String documentType) throws Exception {
        Map<String, Object> document = DBHelper.getInstance().getDocument(id, versiune, documentType);
        int initiator = (int) document.get("id_initiator");
        int aprobare = (int) document.get("id_aprobare");

        if (isApprovalFinal(initiator, aprobare)) {
            markDocumentFinalized();
        } else {
            int nextApproval = getNextApproval(initiator, aprobare);
            if (nextApproval == -1) {
                throw new Exception("Something went wrong! Next Approval not found in the database");
            }
            DBHelper.getInstance().updateDocumentStatus(id, versiune, nextApproval, documentType);

        }

    }

    private int getNextApproval(int initiator, int aprobare) {
        List<Map<String, Object>> flow = DBHelper.getInstance().getFlowForUserType(initiator);

        for (Map row : flow) {
            if ((int) row.get("id_tip_avizare") == aprobare) {
                Map nextRow = flow.get(flow.indexOf(row) + 1);
                return (int) nextRow.get("id_tip_avizare");
            }
        }
        return -1;
    }

    private void markDocumentFinalized() throws Exception {
        throw new Exception("Not implemented yet!");
    }

    private boolean isApprovalFinal(int initiator, int aprobare) {
        List<Map<String, Object>> flow = DBHelper.getInstance().getFlowForUserType(initiator);

        for (Map row : flow) {
            if ((int) row.get("id_tip_avizare") == aprobare && (boolean) row.get("final")) {
                return true;
            }
        }

        return false;
    }

    private int getFirstApprovalNeededForDocument(int userTypeId) {
        List<Map<String, Object>> result = DBHelper.getInstance().getFlowForUserType(userTypeId);
        return (int) result.get(0).get("id_tip_avizare");
    }


    public String getDocumentById(String username, float versiune, int idDocument,String docType) {
        List<Map<String, Object>> documents = DBHelper.getInstance().getAllDocumentsForUser(username,docType);
        Gson gson = new Gson();
        for (Map row : documents) {
            if ((Integer) row.get("id_dispozitie") == idDocument && (Float) row.get("versiune") == versiune)
                return gson.toJson(row);
        }

        return null;
    }

    public String getAllDocumetsForList(String username, Controller.DOCUMENTS_TYPE docType) {
        List<Map<String, Object>> documents = null;
        ArrayList<DocumentListItem> items = new ArrayList<>();

        if (docType == Controller.DOCUMENTS_TYPE.DISPOZITIA_RECTORULUI) {
            documents = DBHelper.getInstance().getAllDRForUser(username);
            for (Map row : documents) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Dispozitia rectorului",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
        } else if (docType == Controller.DOCUMENTS_TYPE.REFERAT_NECESITATE) {
            documents = DBHelper.getInstance().getAllRNForUser(username);
            for (Map row : documents) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Referat necesitate",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
        } else if (docType == null) {
            documents = DBHelper.getInstance().getAllDRForUser(username);
            for (Map row : documents) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Dispozitia rectorului",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
            //TODO: uncomment this when support for RN is added
            documents = DBHelper.getInstance().getAllRNForUser(username);
            for (Map row : documents) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Referat necesitate",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
        }

        Gson gson = new Gson();
        return gson.toJson(items);
    }

    private String getApprovalName(Integer id_aprobare) {
        if (id_aprobare == null || id_aprobare == 0) {
            return "";
        }

        List<Map<String, Object>> approvals = DBHelper.getInstance().getAllApprovals();
        for (Map row : approvals) {
            if (row.get("id").equals(id_aprobare)) {
                return (String) row.get("descriere");
            }
        }
        return "";
    }

    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        return dtf.format(localDate);
    }

    private String getDocumentDate(int id, String documentType) {
        return DBHelper.getInstance().getDocumentDate(id, documentType);
    }

    private String getDocumentJson(int id, float versiune, String documentType) {
        return DBHelper.getInstance().getDocumentJson(id, versiune, documentType);
    }

    public void removeDocument(String idDoc,String versiune, String docType) {
        DBHelper.getInstance().deleteDocument(idDoc,versiune, docType);
    }

    public String getAllDocumetsForReviewForList(String username) {
        List<Map<String, Object>> documents = DBHelper.getInstance().getAllDRForUser(username);
        List<DocumentListItem> items = new ArrayList<>();

        int userAuthority = DBHelper.getInstance().getUserTypeId(username);

        for (Map row : documents) {
            if (userAuthority == (int) row.get("id_aprobare")) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Dispozitia rectorului",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
        }
        documents = DBHelper.getInstance().getAllRNForUser(username);
        for (Map row : documents) {
            if (userAuthority == (int) row.get("id_aprobare")) {
                DocumentListItem item = new DocumentListItem(
                        (int) row.get("id_dispozitie"),
                        (float) row.get("versiune"),
                        (String) row.get("data"),
                        "Referat necesitate",
                        getApprovalName((Integer) row.get("id_aprobare"))
                );
                items.add(item);
            }
        }

        Gson gson = new Gson();
        return gson.toJson(items);
    }
}
