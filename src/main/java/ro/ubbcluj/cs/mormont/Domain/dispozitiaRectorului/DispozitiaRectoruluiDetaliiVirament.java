package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 27/11/2016.
 */
public class DispozitiaRectoruluiDetaliiVirament {
    private String titularContNume;
    private String titularContPrenume;
    private String cnp;
    private String domiciliu;
    private String numeBanca;
    private String iban;
    private String data;

    public DispozitiaRectoruluiDetaliiVirament(String titularContNume, String titularContPrenume, String cnp, String domiciliu, String numeBanca, String iban, String data) {
        this.titularContNume = titularContNume;
        this.titularContPrenume = titularContPrenume;
        this.cnp = cnp;
        this.domiciliu = domiciliu;
        this.numeBanca = numeBanca;
        this.iban = iban;
        this.data = data;
    }

    public String getTitularContNume() {
        return titularContNume;
    }

    public void setTitularContNume(String titularContNume) {
        this.titularContNume = titularContNume;
    }

    public String getTitularContPrenume() {
        return titularContPrenume;
    }

    public void setTitularContPrenume(String titularContPrenume) {
        this.titularContPrenume = titularContPrenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getDomiciliu() {
        return domiciliu;
    }

    public void setDomiciliu(String domiciliu) {
        this.domiciliu = domiciliu;
    }

    public String getNumeBanca() {
        return numeBanca;
    }

    public void setNumeBanca(String numeBanca) {
        this.numeBanca = numeBanca;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
