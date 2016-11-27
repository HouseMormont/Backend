package ro.ubbcluj.cs.mormont.repository;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by tudorlozba on 26/11/2016.
 */

interface DocumentsRepository<DocType> {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";


}