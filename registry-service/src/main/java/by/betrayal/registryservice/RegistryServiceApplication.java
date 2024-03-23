package by.betrayal.registryservice;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RegistryServiceApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

}
