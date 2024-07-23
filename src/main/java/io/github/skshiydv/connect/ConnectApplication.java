package io.github.skshiydv.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConnectApplication {

    private static final Logger log = LoggerFactory.getLogger(ConnectApplication.class);

    public static void main(String[] args) {
      log.info("Starting application");
        SpringApplication.run(ConnectApplication.class, args);
    }

}
