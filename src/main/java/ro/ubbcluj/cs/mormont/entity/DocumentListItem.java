package ro.ubbcluj.cs.mormont.entity;

/**
 * Created by tudorlozba on 04/01/2017.
 */
public class DocumentListItem {
    private int id;
    private float versiune;
    private String data;

    public String getTipDocumet() {
        return tipDocumet;
    }

    public void setTipDocumet(String tipDocumet) {
        this.tipDocumet = tipDocumet;
    }

    public String getAveizare() {
        return aveizare;
    }

    public void setAveizare(String aveizare) {
        this.aveizare = aveizare;
    }

    private String tipDocumet;
    private String aveizare;

    public DocumentListItem(int id, float versiune, String data, String tip, String avizare) {
        this.id = id;
        this.versiune = versiune;
        this.data = data;
        this.tipDocumet = tip;
        this.aveizare = avizare;
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
