package by.betrayal.audienceservice.core.utils.generator;

import by.betrayal.audienceservice.core.FakerUtils;
import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AudienceGenerator {

    public static AudienceEntity generateAudience() {
        var faker = FakerUtils.FAKER;
        var audience = new AudienceEntity();
        audience.setName(String.valueOf(faker.number().numberBetween(100, 1000)));
        audience.setType(AudienceTypeGenerator.generateTypeWithId());
        audience.setCorpus(CorpusGenerator.generateCorpusWithId());
        return audience;
    }

    public static List<AudienceEntity> generateAudiences(int count) {
        var audiences = new ArrayList<AudienceEntity>(count);

        for (int i = 0; i < count; i++) {
            audiences.add(generateAudienceWithId());
        }

        return audiences;
    }

    public static AudienceEntity generateAudienceWithId() {
        var faker = FakerUtils.FAKER;
        var audience = generateAudience();
        audience.setId(faker.random().nextLong(1, Long.MAX_VALUE));
        return audience;
    }

    public static AudienceCreateDto generateCreateAudience(Long corpusId, Short typeId) {
        var faker = FakerUtils.FAKER;

        var audience = AudienceCreateDto.builder()
                .name(String.valueOf(faker.number().numberBetween(100, 1000)))
                .corpusId(corpusId)
                .typeId(typeId)
                .build();

        return audience;
    }

    public static AudienceUpdateDto generateUpdateAudience(Long id, Long corpusId, Short typeId) {
        var faker = FakerUtils.FAKER;

        var audience = AudienceUpdateDto.builder()
                .id(id)
                .name(String.valueOf(faker.number().numberBetween(100, 1000)))
                .corpusId(corpusId)
                .typeId(typeId)
                .build();

        return audience;
    }
}
