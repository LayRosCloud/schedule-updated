package by.betrayal.audienceservice.core.utils.generator;

import by.betrayal.audienceservice.core.FakerUtils;
import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.entity.CorpusEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class CorpusGenerator {

    public static CorpusEntity generateCorpus() {
        var faker = FakerUtils.FAKER;
        var corpus = new CorpusEntity();
        corpus.setName("Corpus " + faker.random().nextLong(1, 20));
        corpus.setInstitution(InstitutionGenerator.generateInstitution());
        return corpus;
    }

    public static CorpusEntity generateCorpusWithId() {
        var faker = FakerUtils.FAKER;
        var corpus = generateCorpus();
        corpus.setId(faker.random().nextLong(1, Long.MAX_VALUE));
        return corpus;
    }

    public static List<CorpusEntity> generateCorpusList(int count) {
        var types = new ArrayList<CorpusEntity>(count);

        for (int i = 0; i < count; i++) {
            types.add(generateCorpusWithId());
        }

        return types;
    }

    public static CorpusCreateDto generateCreateCorpus(Long institutionId) {
        var faker = FakerUtils.FAKER;

        var corpus = CorpusCreateDto.builder()
                .name("Corpus " + faker.random().nextLong(1, 20))
                .address(faker.address().fullAddress())
                .institutionId(institutionId)
                .build();

        return corpus;
    }

    public static CorpusUpdateDto generateUpdateCorpus(Long id, Long institutionId) {
        var faker = FakerUtils.FAKER;

        var corpus = CorpusUpdateDto.builder()
                .id(id)
                .name("Corpus " + faker.random().nextLong(1, 20))
                .address(faker.address().fullAddress())
                .institutionId(institutionId)
                .build();

        return corpus;
    }
}
