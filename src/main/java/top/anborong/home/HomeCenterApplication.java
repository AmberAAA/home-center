package top.anborong.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HomeCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeCenterApplication.class, args);
    }

}
