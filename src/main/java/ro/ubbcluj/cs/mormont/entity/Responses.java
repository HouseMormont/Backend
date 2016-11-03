package ro.ubbcluj.cs.mormont.entity;

/**
 * ****************************************
 * Created by Tirla Alin on 3/11/2016.  *
 * ****************************************
 */
public enum Responses {
    MESSAGE("Message"),
    AUTHORITIES("Authorities");

    private final String responseName;
    Responses(String responseName) {
        this.responseName = responseName;
    }
    public String getValue() {
        return responseName;
    }
}
