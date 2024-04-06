package by.betrayal.audienceservice;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AudienceServiceApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AudienceServiceApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

}
