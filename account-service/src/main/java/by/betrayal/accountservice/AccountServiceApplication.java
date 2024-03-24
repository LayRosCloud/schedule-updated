package by.betrayal.accountservice;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class AccountServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AccountServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
