package by.betrayal.personalservice.core.utils.generator;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

public class ImageGenerator {

    public static MockMultipartFile generateImage() {
        return new MockMultipartFile(
                "example",
                "image.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "HELLO, WORLD!".getBytes()
        );
    }
}
