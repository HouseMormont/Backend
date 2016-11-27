package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 27/11/2016.
 */
public class DispozitiaRectoruluiDeclaratie {
    private String numeSubsemnat;
    private String prenumeSubsemnat;
    private String destinatie;
    private String dataInceput;
    private String dataSfarsit;
    private String dataCurenta;
    private String dataPlecare;
    private String dataSosire;
    private float suma;
    private float sumaAutoturism;

    public DispozitiaRectoruluiDeclaratie(String numeSubsemnat, String prenumeSubsemnat, String destinatie, String dataInceput, String dataSfarsit, String dataCurenta, String dataPlecare, String dataSosire, float suma, float sumaAutoturism) {
        this.numeSubsemnat = numeSubsemnat;
        this.prenumeSubsemnat = prenumeSubsemnat;
        this.destinatie = destinatie;
        this.dataInceput = dataInceput;
        this.dataSfarsit = dataSfarsit;
        this.dataCurenta = dataCurenta;
        this.dataPlecare = dataPlecare;
        this.dataSosire = dataSosire;
        this.suma = suma;
        this.sumaAutoturism = sumaAutoturism;
    }

    public String getNumeSubsemnat() {
        return numeSubsemnat;
    }

    public void setNumeSubsemnat(String numeSubsemnat) {
        this.numeSubsemnat = numeSubsemnat;
    }

    public String getPrenumeSubsemnat() {
        return prenumeSubsemnat;
    }

    public void setPrenumeSubsemnat(String prenumeSubsemnat) {
        this.prenumeSubsemnat = prenumeSubsemnat;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
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

    public String getDataCurenta() {
        return dataCurenta;
    }

    public void setDataCurenta(String dataCurenta) {
        this.dataCurenta = dataCurenta;
    }

    public String getDataPlecare() {
        return dataPlecare;
    }

    public void setDataPlecare(String dataPlecare) {
        this.dataPlecare = dataPlecare;
    }

    public String getDataSosire() {
        return dataSosire;
    }

    public void setDataSosire(String dataSosire) {
        this.dataSosire = dataSosire;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    public float getSumaAutoturism() {
        return sumaAutoturism;
    }

    public void setSumaAutoturism(float sumaAutoturism) {
        this.sumaAutoturism = sumaAutoturism;
    }
}
