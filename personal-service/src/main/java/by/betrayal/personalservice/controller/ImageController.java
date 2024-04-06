package by.betrayal.personalservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = "v1/images")
public class ImageController {

    public static final String PATHNAME = "src/main/resources/static/";
    private static final String ENDPOINT_IMAGE_NAME = "{imageName}";

    @GetMapping(ENDPOINT_IMAGE_NAME)
    public ResponseEntity<byte[]> download(@PathVariable String imageName) throws IOException {
        String absolutePath = new File(PATHNAME).getAbsolutePath();
        Path imagePath = Paths.get(absolutePath).resolve(imageName);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] bytesImage = Files.readAllBytes(imagePath);

        var response = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesImage);

        return response;
    }
}
