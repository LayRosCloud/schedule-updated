package by.betrayal.audienceservice.core.utils.generator;

import by.betrayal.audienceservice.core.FakerUtils;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AudienceTypeGenerator {

    public static AudienceTypeEntity generateType() {
        var faker = FakerUtils.FAKER;
        var type = new AudienceTypeEntity();
        type.setName(String.valueOf(faker.number().numberBetween(1, 1000)));
        return type;
    }

    public static List<AudienceTypeEntity> generateTypes(int count) {
        var types = new ArrayList<AudienceTypeEntity>(count);

        for (int i = 0; i < count; i++) {
            types.add(generateTypeWithId());
        }

        return types;
    }

    public static AudienceTypeEntity generateTypeWithId() {
        var faker = FakerUtils.FAKER;
        var type = generateType();
        type.setId((short) faker.random().nextLong(1, Short.MAX_VALUE));
        return type;
    }

    public static AudienceTypeCreateDto generateCreateType() {
        var faker = FakerUtils.FAKER;
        var type = AudienceTypeCreateDto.builder()
                .name(String.valueOf(faker.number().numberBetween(1, 1000)))
                .build();
        return type;
    }

    public static AudienceTypeUpdateDto generateUpdateType(Short id) {
        var faker = FakerUtils.FAKER;
        var type = AudienceTypeUpdateDto.builder()
                .id(id)
                .name(String.valueOf(faker.number().numberBetween(1, 1000)))
                .build();
        return type;
    }
}
