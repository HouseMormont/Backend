package ro.ubbcluj.cs.mormont.Domain.dispozitiaRectorului;

/**
 * Created by tudorlozba on 26/11/2016.
 */
public class DispozitiaRectorului {

    private int idDispozitie;
    private float versiune;
    private String username;

    private DetaliiGenerale detaliiGenerale;
    private DetaliiTransport detaliiTransport;
    private DetaliiDiurna detaliiDiurna;
    private DetaliiCazare detaliiCazare;
    private AlteCheltuieli alteCheltuieli;

    private float sumaAvansSolicitat;

    private String dataCererii;
    private String numeDirectorDepartament;
    private String numeDecan;
    private String numeDirectorProiect;
    private String numeDirectorScoalaDoctorala;

    public DispozitiaRectorului(int idDispozitie, float versiune, String username, DetaliiGenerale detaliiGenerale, DetaliiTransport detaliiTransport, DetaliiDiurna detaliiDiurna, DetaliiCazare detaliiCazare, AlteCheltuieli alteCheltuieli, float sumaAvansSolicitat, String dataCererii, String numeDirectorDepartament, String numeDecan, String numeDirectorProiect, String numeDirectorScoalaDoctorala) {
        this.idDispozitie = idDispozitie;
        this.versiune = versiune;
        this.username = username;
        this.detaliiGenerale = detaliiGenerale;
        this.detaliiTransport = detaliiTransport;
        this.detaliiDiurna = detaliiDiurna;
        this.detaliiCazare = detaliiCazare;
        this.alteCheltuieli = alteCheltuieli;
        this.sumaAvansSolicitat = sumaAvansSolicitat;
        this.dataCererii = dataCererii;
        this.numeDirectorDepartament = numeDirectorDepartament;
        this.numeDecan = numeDecan;
        this.numeDirectorProiect = numeDirectorProiect;
        this.numeDirectorScoalaDoctorala = numeDirectorScoalaDoctorala;
    }


    public int getIdDispozitie() {
        return idDispozitie;
    }

    public void setIdDispozitie(int idDispozitie) {
        this.idDispozitie = idDispozitie;
    }

    public float getVersiune() {
        return versiune;
    }

    public void setVersiune(float versiune) {
        this.versiune = versiune;
    }

    public DetaliiGenerale getDetaliiGenerale() {
        return detaliiGenerale;
    }

    public void setDetaliiGenerale(DetaliiGenerale detaliiGenerale) {
        this.detaliiGenerale = detaliiGenerale;
    }

    public DetaliiTransport getDetaliiTransport() {
        return detaliiTransport;
    }

    public void setDetaliiTransport(DetaliiTransport detaliiTransport) {
        this.detaliiTransport = detaliiTransport;
    }

    public DetaliiDiurna getDetaliiDiurna() {
        return detaliiDiurna;
    }

    public void setDetaliiDiurna(DetaliiDiurna detaliiDiurna) {
        this.detaliiDiurna = detaliiDiurna;
    }

    public DetaliiCazare getDetaliiCazare() {
        return detaliiCazare;
    }

    public void setDetaliiCazare(DetaliiCazare detaliiCazare) {
        this.detaliiCazare = detaliiCazare;
    }

    public AlteCheltuieli getAlteCheltuieli() {
        return alteCheltuieli;
    }

    public void setAlteCheltuieli(AlteCheltuieli alteCheltuieli) {
        this.alteCheltuieli = alteCheltuieli;
    }

    public float getSumaAvansSolicitat() {
        return sumaAvansSolicitat;
    }

    public void setSumaAvansSolicitat(float sumaAvansSolicitat) {
        this.sumaAvansSolicitat = sumaAvansSolicitat;
    }

    public String getDataCererii() {
        return dataCererii;
    }

    public void setDataCererii(String dataCererii) {
        this.dataCererii = dataCererii;
    }

    public String getNumeDirectorDepartament() {
        return numeDirectorDepartament;
    }

    public void setNumeDirectorDepartament(String numeDirectorDepartament) {
        this.numeDirectorDepartament = numeDirectorDepartament;
    }

    public String getNumeDecan() {
        return numeDecan;
    }

    public void setNumeDecan(String numeDecan) {
        this.numeDecan = numeDecan;
    }

    public String getNumeDirectorProiect() {
        return numeDirectorProiect;
    }

    public void setNumeDirectorProiect(String numeDirectorProiect) {
        this.numeDirectorProiect = numeDirectorProiect;
    }

    public String getNumeDirectorScoalaDoctorala() {
        return numeDirectorScoalaDoctorala;
    }

    public void setNumeDirectorScoalaDoctorala(String numeDirectorScoalaDoctorala) {
        this.numeDirectorScoalaDoctorala = numeDirectorScoalaDoctorala;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
