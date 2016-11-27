package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 26/11/2016.
 */
public class DetaliiTransport {

    public DetaliiTransport(float sumaATM, int valutaATM, float sumaAutoIntern, int valutaAutoIntern, float sumaAutoExtern, int valutaAutoExtern, float sumaTransportErasmus, int valutaTrasportErasmu, String formaFinantare, float sumaTransportInternDestinatie, int valutaTransportInternDestinatie, String finantareTransportInternDestinatie) {
        this.sumaATM = sumaATM;
        this.valutaATM = valutaATM;
        this.sumaAutoIntern = sumaAutoIntern;
        this.valutaAutoIntern = valutaAutoIntern;
        this.sumaAutoExtern = sumaAutoExtern;
        this.valutaAutoExtern = valutaAutoExtern;
        this.sumaTransportErasmus = sumaTransportErasmus;
        this.valutaTrasportErasmu = valutaTrasportErasmu;
        this.formaFinantare = formaFinantare;
        this.sumaTransportInternDestinatie = sumaTransportInternDestinatie;
        this.valutaTransportInternDestinatie = valutaTransportInternDestinatie;
        this.finantareTransportInternDestinatie = finantareTransportInternDestinatie;
    }

    public float getSumaATM() {
        return sumaATM;
    }

    public void setSumaATM(float sumaATM) {
        this.sumaATM = sumaATM;
    }

    public int getValutaATM() {
        return valutaATM;
    }

    public void setValutaATM(int valutaATM) {
        this.valutaATM = valutaATM;
    }

    public float getSumaAutoIntern() {
        return sumaAutoIntern;
    }

    public void setSumaAutoIntern(float sumaAutoIntern) {
        this.sumaAutoIntern = sumaAutoIntern;
    }

    public int getValutaAutoIntern() {
        return valutaAutoIntern;
    }

    public void setValutaAutoIntern(int valutaAutoIntern) {
        this.valutaAutoIntern = valutaAutoIntern;
    }

    public float getSumaAutoExtern() {
        return sumaAutoExtern;
    }

    public void setSumaAutoExtern(float sumaAutoExtern) {
        this.sumaAutoExtern = sumaAutoExtern;
    }

    public int getValutaAutoExtern() {
        return valutaAutoExtern;
    }

    public void setValutaAutoExtern(int valutaAutoExtern) {
        this.valutaAutoExtern = valutaAutoExtern;
    }

    public float getSumaTransportErasmus() {
        return sumaTransportErasmus;
    }

    public void setSumaTransportErasmus(float sumaTransportErasmus) {
        this.sumaTransportErasmus = sumaTransportErasmus;
    }

    public int getValutaTrasportErasmu() {
        return valutaTrasportErasmu;
    }

    public void setValutaTrasportErasmu(int valutaTrasportErasmu) {
        this.valutaTrasportErasmu = valutaTrasportErasmu;
    }

    public String getFormaFinantare() {
        return formaFinantare;
    }

    public void setFormaFinantare(String formaFinantare) {
        this.formaFinantare = formaFinantare;
    }

    public float getSumaTransportInternDestinatie() {
        return sumaTransportInternDestinatie;
    }

    public void setSumaTransportInternDestinatie(float sumaTransportInternDestinatie) {
        this.sumaTransportInternDestinatie = sumaTransportInternDestinatie;
    }

    public int getValutaTransportInternDestinatie() {
        return valutaTransportInternDestinatie;
    }

    public void setValutaTransportInternDestinatie(int valutaTransportInternDestinatie) {
        this.valutaTransportInternDestinatie = valutaTransportInternDestinatie;
    }

    public String getFinantareTransportInternDestinatie() {
        return finantareTransportInternDestinatie;
    }

    public void setFinantareTransportInternDestinatie(String finantareTransportInternDestinatie) {
        this.finantareTransportInternDestinatie = finantareTransportInternDestinatie;
    }

    private float sumaATM;
    private int valutaATM;

    private float sumaAutoIntern;
    private int valutaAutoIntern;

    private float sumaAutoExtern;
    private int valutaAutoExtern;

    private float sumaTransportErasmus;
    private int valutaTrasportErasmu; //TODO only value shuld be EUR
    private String formaFinantare;

    private float sumaTransportInternDestinatie;
    private int valutaTransportInternDestinatie;
    private String finantareTransportInternDestinatie;



}
