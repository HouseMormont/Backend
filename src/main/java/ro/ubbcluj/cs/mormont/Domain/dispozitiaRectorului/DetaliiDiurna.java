package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 26/11/2016.
 */
public class DetaliiDiurna {

    private float sumaDiurna;
    private int nrZileDiurna;
    private float totalDiurna;
    private int valutaDiurna;
    private String finantareDiurna;

    private float sumaSubzistenta;
    private int nrZileSubzistenta;
    private int valutaSubzistenta;
    private String finantareSubzistenta;

    private float sumaMobilitate;
    private int nrLuniMobilitate;
    private float totalMobiitate;
    private int valutaMobilitate;
    private String finantareMobilitate;


    public DetaliiDiurna(float sumaDiurna, int nrZileDiurna, float totalDiurna, int valutaDiurna, String finantareDiurna, float sumaSubzistenta, int nrZileSubzistenta, int valutaSubzistenta, String finantareSubzistenta, float sumaMobilitate, int nrLuniMobilitate, float totalMobiitate, int valutaMobilitate, String finantareMobilitate) {
        this.setSumaDiurna(sumaDiurna);
        this.setNrZileDiurna(nrZileDiurna);
        this.setTotalDiurna(totalDiurna);
        this.setValutaDiurna(valutaDiurna);
        this.setFinantareDiurna(finantareDiurna);
        this.setSumaSubzistenta(sumaSubzistenta);
        this.setNrZileSubzistenta(nrZileSubzistenta);
        this.setValutaSubzistenta(valutaSubzistenta);
        this.setFinantareSubzistenta(finantareSubzistenta);
        this.setSumaMobilitate(sumaMobilitate);
        this.setNrLuniMobilitate(nrLuniMobilitate);
        this.setTotalMobiitate(totalMobiitate);
        this.setValutaMobilitate(valutaMobilitate);
        this.setFinantareMobilitate(finantareMobilitate);
    }

    public float getSumaDiurna() {
        return sumaDiurna;
    }

    public void setSumaDiurna(float sumaDiurna) {
        this.sumaDiurna = sumaDiurna;
    }

    public int getNrZileDiurna() {
        return nrZileDiurna;
    }

    public void setNrZileDiurna(int nrZileDiurna) {
        this.nrZileDiurna = nrZileDiurna;
    }

    public float getTotalDiurna() {
        return totalDiurna;
    }

    public void setTotalDiurna(float totalDiurna) {
        this.totalDiurna = totalDiurna;
    }

    public int getValutaDiurna() {
        return valutaDiurna;
    }

    public void setValutaDiurna(int valutaDiurna) {
        this.valutaDiurna = valutaDiurna;
    }

    public String getFinantareDiurna() {
        return finantareDiurna;
    }

    public void setFinantareDiurna(String finantareDiurna) {
        this.finantareDiurna = finantareDiurna;
    }

    public float getSumaSubzistenta() {
        return sumaSubzistenta;
    }

    public void setSumaSubzistenta(float sumaSubzistenta) {
        this.sumaSubzistenta = sumaSubzistenta;
    }

    public int getNrZileSubzistenta() {
        return nrZileSubzistenta;
    }

    public void setNrZileSubzistenta(int nrZileSubzistenta) {
        this.nrZileSubzistenta = nrZileSubzistenta;
    }

    public int getValutaSubzistenta() {
        return valutaSubzistenta;
    }

    public void setValutaSubzistenta(int valutaSubzistenta) {
        this.valutaSubzistenta = valutaSubzistenta;
    }

    public String getFinantareSubzistenta() {
        return finantareSubzistenta;
    }

    public void setFinantareSubzistenta(String finantareSubzistenta) {
        this.finantareSubzistenta = finantareSubzistenta;
    }

    public float getSumaMobilitate() {
        return sumaMobilitate;
    }

    public void setSumaMobilitate(float sumaMobilitate) {
        this.sumaMobilitate = sumaMobilitate;
    }

    public int getNrLuniMobilitate() {
        return nrLuniMobilitate;
    }

    public void setNrLuniMobilitate(int nrLuniMobilitate) {
        this.nrLuniMobilitate = nrLuniMobilitate;
    }

    public float getTotalMobiitate() {
        return totalMobiitate;
    }

    public void setTotalMobiitate(float totalMobiitate) {
        this.totalMobiitate = totalMobiitate;
    }

    public int getValutaMobilitate() {
        return valutaMobilitate;
    }

    public void setValutaMobilitate(int valutaMobilitate) {
        this.valutaMobilitate = valutaMobilitate;
    }

    public String getFinantareMobilitate() {
        return finantareMobilitate;
    }

    public void setFinantareMobilitate(String finantareMobilitate) {
        this.finantareMobilitate = finantareMobilitate;
    }
}
