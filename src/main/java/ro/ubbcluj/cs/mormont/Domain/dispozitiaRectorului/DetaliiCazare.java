package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 26/11/2016.
 */
public class DetaliiCazare {

    private float sumaDestinatie;
    private int nrZileDestinatie;
    private float totalDestinatie;
    private int valutaDestinatie;
    private String finantareDestinatie;

    private float sumaCalatorie;
    private int nrZileCalatorie;
    private float totalCalatorie;
    private int valutaCalatorie;
    private String finantareCalatorie;

    public DetaliiCazare(float sumaDestinatie, int nrZileDestinatie, float totalDestinatie, int valutaDestinatie, String finantareDestinatie, float sumaCalatorie, int nrZileCalatorie, float totalCalatorie, int valutaCalatorie, String finantareCalatorie) {
        this.sumaDestinatie = sumaDestinatie;
        this.nrZileDestinatie = nrZileDestinatie;
        this.totalDestinatie = totalDestinatie;
        this.valutaDestinatie = valutaDestinatie;
        this.finantareDestinatie = finantareDestinatie;
        this.sumaCalatorie = sumaCalatorie;
        this.nrZileCalatorie = nrZileCalatorie;
        this.totalCalatorie = totalCalatorie;
        this.valutaCalatorie = valutaCalatorie;
        this.finantareCalatorie = finantareCalatorie;
    }

    public float getSumaDestinatie() {
        return sumaDestinatie;
    }

    public void setSumaDestinatie(float sumaDestinatie) {
        this.sumaDestinatie = sumaDestinatie;
    }

    public int getNrZileDestinatie() {
        return nrZileDestinatie;
    }

    public void setNrZileDestinatie(int nrZileDestinatie) {
        this.nrZileDestinatie = nrZileDestinatie;
    }

    public float getTotalDestinatie() {
        return totalDestinatie;
    }

    public void setTotalDestinatie(float totalDestinatie) {
        this.totalDestinatie = totalDestinatie;
    }

    public int getValutaDestinatie() {
        return valutaDestinatie;
    }

    public void setValutaDestinatie(int valutaDestinatie) {
        this.valutaDestinatie = valutaDestinatie;
    }

    public String getFinantareDestinatie() {
        return finantareDestinatie;
    }

    public void setFinantareDestinatie(String finantareDestinatie) {
        this.finantareDestinatie = finantareDestinatie;
    }

    public float getSumaCalatorie() {
        return sumaCalatorie;
    }

    public void setSumaCalatorie(float sumaCalatorie) {
        this.sumaCalatorie = sumaCalatorie;
    }

    public int getNrZileCalatorie() {
        return nrZileCalatorie;
    }

    public void setNrZileCalatorie(int nrZileCalatorie) {
        this.nrZileCalatorie = nrZileCalatorie;
    }

    public float getTotalCalatorie() {
        return totalCalatorie;
    }

    public void setTotalCalatorie(float totalCalatorie) {
        this.totalCalatorie = totalCalatorie;
    }

    public int getValutaCalatorie() {
        return valutaCalatorie;
    }

    public void setValutaCalatorie(int valutaCalatorie) {
        this.valutaCalatorie = valutaCalatorie;
    }

    public String getFinantareCalatorie() {
        return finantareCalatorie;
    }

    public void setFinantareCalatorie(String finantareCalatorie) {
        this.finantareCalatorie = finantareCalatorie;
    }
}
