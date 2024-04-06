package by.betrayal.audienceservice.core.utils.generator;

import by.betrayal.audienceservice.core.FakerUtils;
import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.entity.InstitutionEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class InstitutionGenerator {

    public static InstitutionEntity generateInstitution() {
        var faker = FakerUtils.FAKER;
        var type = new InstitutionEntity();
        type.setName(faker.name().title());
        return type;
    }

    public static List<InstitutionEntity> generateInstitutionList(int count) {
        var institutionList = new ArrayList<InstitutionEntity>(count);

        for (int i = 0; i < count; i++) {
            institutionList.add(generateInstitutionWithId());
        }

        return institutionList;
    }

    public static InstitutionEntity generateInstitutionWithId() {
        var faker = FakerUtils.FAKER;
        var type = generateInstitution();
        type.setId(faker.random().nextLong(1, Long.MAX_VALUE));
        return type;
    }

    public static InstitutionCreateDto generateCreateInstitution() {
        var faker = FakerUtils.FAKER;
        var institution = InstitutionCreateDto.builder()
                .name(faker.name().title())
                .build();
        return institution;
    }

    public static InstitutionUpdateDto generateUpdateInstitution(Long id) {
        var faker = FakerUtils.FAKER;
        var institution = InstitutionUpdateDto.builder()
                .id(id)
                .name(faker.name().title())
                .build();
        return institution;
    }
}
