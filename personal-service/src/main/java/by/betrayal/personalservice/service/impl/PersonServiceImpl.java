package by.betrayal.personalservice.service.impl;

import by.betrayal.personalservice.controller.ImageController;
import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonFullDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.entity.PersonEntity;
import by.betrayal.personalservice.mapper.PersonMapper;
import by.betrayal.personalservice.repository.PersonRepository;
import by.betrayal.personalservice.service.PersonService;
import by.betrayal.personalservice.utils.ThrowableUtils;
import by.betrayal.personalservice.utils.pageable.PageContainer;
import by.betrayal.personalservice.utils.pageable.PageOptions;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageContainer<PersonFullDto> findAll(PageOptions options) {
        var sort = Sort.by("lastName").ascending();
        var pageable = PageRequest.of(options.page(), options.limit(), sort);
        var peoplePage = repository.findAll(pageable);
        var people = mapper.mapToFullDto(peoplePage.toList());
        return new PageContainer<>(people, peoplePage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonFullDto> findAll() {
        var list = repository.findAll();
        return mapper.mapToFullDto(list);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonFullDto findById(Long id) {
        var item = findByIdOrThrowNotFoundException(id);
        return mapper.mapToFullDto(item);
    }

    @Override
    @Transactional
    public PersonFullDto create(PersonCreateDto dto) {
        try {
            var photo = dto.getPhoto();
            String filename = null;

            if(photo != null) {
                filename = upload(photo);
            }

            var entity = mapper.mapToEntity(dto);
            entity.setPhoto(filename);

            var user = repository.save(entity);

            return mapper.mapToFullDto(user);
        } catch (IOException ex) {
            throw ThrowableUtils.getBadRequestException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public PersonFullDto update(PersonUpdateDto dto) {
        var person = findByIdOrThrowNotFoundException(dto.getId());

        mapper.mapToEntity(person, dto);

        var result = repository.save(person);

        return mapper.mapToFullDto(result);
    }

    @Override
    @Transactional
    public PersonFullDto delete(Long id) {
        var item = findByIdOrThrowNotFoundException(id);

        repository.delete(item);

        return mapper.mapToFullDto(item);
    }

    private String upload(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }

        var bytesPhoto = file.getBytes();
        var filename = UUID.randomUUID() + ".jpg";
        var absolutePath = new File(ImageController.PATHNAME).getAbsolutePath();
        var filePath = Paths.get(absolutePath, filename);
        Files.write(filePath, bytesPhoto);
        return filename;
    }

    private PersonEntity findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Person with id %d is not found", id)
        );
    }
}
