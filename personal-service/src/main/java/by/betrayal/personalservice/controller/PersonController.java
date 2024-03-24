package by.betrayal.personalservice.controller;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.entity.PersonEntity;
import by.betrayal.personalservice.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "v1/people")
public class PersonController {

    private final PersonService service;

    @GetMapping
    public ResponseEntity<List<PersonFullDto>> findAll() {
        var list = service.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> download(@PathVariable String imageName) throws IOException {
        String absolutePath = new File("personal-service/src/main/resources/static/").getAbsolutePath();
        Path imagePath = Paths.get(absolutePath).resolve(imageName);

        if (!Files.exists(imagePath)) {
            return ResponseEntity.notFound().build();
        }

        byte[] bytesImage = Files.readAllBytes(imagePath);

        var response = ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytesImage);

        return response;
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonFullDto> findById(@PathVariable Long id) {
        var item = service.findById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PersonFullDto> create(@ModelAttribute PersonCreateDto dto) {

        var item = service.create(dto);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

}
