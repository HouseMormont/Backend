package ro.ubbcluj.cs.mormont;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 ****************************************
 * Created by Tirla Alin on 28.12.2015. *
 ****************************************
 */
@SpringBootApplication
@EnableAutoConfiguration
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }
}
