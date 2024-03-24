package by.betrayal.personalservice.service.impl;

import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.exception.NotFoundException;
import by.betrayal.personalservice.mapper.PersonMapper;
import by.betrayal.personalservice.repository.PersonRepository;
import by.betrayal.personalservice.service.PersonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Override
    public List<PersonFullDto> findAll() {
        var list = repository.findAll();
        return mapper.mapToFullDto(list);
    }

    @Override
    public PersonFullDto findById(Long id) {
        var item = repository.findById(id).orElseThrow(() ->
                new NotFoundException()
        );
        return mapper.mapToFullDto(item);
    }

    @Override
    @Transactional
    public PersonFullDto create(PersonCreateDto dto) {
        try {
            var photo = dto.getPhoto();
            var bytesPhoto = photo.getBytes();

            var filename = UUID.randomUUID() + ".jpg";
            String absolutePath = new File("personal-service/src/main/resources/static/").getAbsolutePath();
            Path filePath = Paths.get(absolutePath, filename);
            Files.write(filePath, bytesPhoto);

            var entity = mapper.mapToEntity(dto);
            entity.setPhoto(filename);
            var user = repository.save(entity);

            return mapper.mapToFullDto(user);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
