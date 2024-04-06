package by.betrayal.personalservice.core.utils.generator;

import by.betrayal.personalservice.core.FakerUtils;
import by.betrayal.personalservice.dto.person.PersonCreateDto;
import by.betrayal.personalservice.dto.person.PersonUpdateDto;
import by.betrayal.personalservice.entity.PersonEntity;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersonGenerator {

    public static PersonEntity generatePerson() {
        var faker = FakerUtils.FAKER;
        var person = new PersonEntity();

        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        person.setPatronymic(faker.name().nameWithMiddle());
        person.setPhoto(UUID.randomUUID() + ".jpg");
        person.setInstitutionId(generateId());

        return person;
    }

    public static PersonEntity generatePersonWithId() {
        var person = generatePerson();
        person.setId(generateId());
        return person;
    }

    public static List<PersonEntity> generatePersons(Integer count) {
        var personList = new ArrayList<PersonEntity>(count);

        for (int i = 0; i < count; i++) {
            personList.add(generatePersonWithId());
        }

        return personList;
    }


    public static PersonUpdateDto generateUpdateDto(Long id) {
        var faker = FakerUtils.FAKER;

        var dto = PersonUpdateDto.builder()
                .id(id)
                .lastName(faker.name().lastName())
                .firstName(faker.name().firstName())
                .patronymic(faker.name().nameWithMiddle())
                .institutionId(generateId())
                .build();
        return dto;
    }

    public static PersonCreateDto generateCreateDto() {
        var faker = FakerUtils.FAKER;

        var dto = PersonCreateDto.builder()
                .lastName(faker.name().lastName())
                .firstName(faker.name().firstName())
                .patronymic(faker.name().nameWithMiddle())
                .institutionId(generateId())
                .photo(ImageGenerator.generateImage()).build();
        return dto;
    }

    private static Long generateId() {
        var faker = FakerUtils.FAKER;
        return faker.random().nextLong(1, Long.MAX_VALUE);
    }
}
