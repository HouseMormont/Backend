package ro.ubbcluj.cs.mormont.entity;

/**
 * ****************************************
 * Created by Tirla Alin on 2/11/2016.  *
 * ****************************************
 */
public enum Headers {
    USERNAME("Username"),
    PASSWORD("Password");

    private final String headerName;
    Headers(String headerName) {
        this.headerName = headerName;
    }
    public String getValue() {
        return headerName;
    }
}
