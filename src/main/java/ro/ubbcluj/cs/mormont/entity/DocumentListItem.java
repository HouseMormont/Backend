package ro.ubbcluj.cs.mormont.entity;

/**
 * Created by tudorlozba on 04/01/2017.
 */
public class DocumentListItem {
    private int id;
    private float versiune;
    private String data;

    public DocumentListItem(int id, float versiune, String data) {
        this.id = id;
        this.versiune = versiune;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public float getVersiune() {
        return versiune;
    }

    public String getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersiune(float versiune) {
        this.versiune = versiune;
    }

    public void setData(String data) {
        this.data = data;
    }
}
