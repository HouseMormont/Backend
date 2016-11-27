package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 26/11/2016.
 */
public class DetaliiGenerale {


    private String nume, prenume;
    private String funtie;

    private int facutate, departament;

    private String ruta;
    private String dataInceput, dataSfarsit;

    private int mijlocTransport;

    private String nrTelefon;
    private String email;

    private String scopDeplasare;

    public DetaliiGenerale(String nume, String prenume, String funtie, int facutate, int departament, String ruta, String dataInceput, String dataSfarsit, int mijlocTransport, String nrTelefon, String email, String scopDeplasare) {

        this.nume = nume;
        this.prenume = prenume;
        this.funtie = funtie;
        this.facutate = facutate;
        this.departament = departament;
        this.ruta = ruta;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.mijlocTransport = mijlocTransport;
        this.nrTelefon = nrTelefon;
        this.email = email;
        this.scopDeplasare = scopDeplasare;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getFuntie() {
        return funtie;
    }

    public void setFuntie(String funtie) {
        this.funtie = funtie;
    }

    public int getFacutate() {
        return facutate;
    }

    public void setFacutate(int facutate) {
        this.facutate = facutate;
    }

    public int getDepartament() {
        return departament;
    }

    public void setDepartament(int departament) {
        this.departament = departament;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDataInceput() {
        return dataInceput;
    }

    public void setDataInceput(String dataInceput) {
        this.dataInceput = dataInceput;
    }

    public String getDataSfarsit() {
        return dataSfarsit;
    }

    public void setDataSfarsit(String dataSfarsit) {
        this.dataSfarsit = dataSfarsit;
    }

    public int getMijlocTransport() {
        return mijlocTransport;
    }

    public void setMijlocTransport(int mijlocTransport) {
        this.mijlocTransport = mijlocTransport;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScopDeplasare() {
        return scopDeplasare;
    }

    public void setScopDeplasare(String scopDeplasare) {
        this.scopDeplasare = scopDeplasare;
    }
}
