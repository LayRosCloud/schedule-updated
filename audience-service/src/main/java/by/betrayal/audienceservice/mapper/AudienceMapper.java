package by.betrayal.audienceservice.mapper;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CorpusMapper.class, AudienceTypeMapper.class})
public interface AudienceMapper {
    List<AudienceFullDto> mapToFullDto(List<AudienceEntity> items);
    AudienceFullDto mapToFullDto(AudienceEntity item);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "corpus", ignore = true)
    AudienceEntity mapToEntity(AudienceCreateDto dto);

    default void mapToEntity(AudienceEntity item, AudienceUpdateDto dto) {
        if (item == null || dto == null) {
            throw ThrowableUtils.getBadRequestException();
        }

        if (!item.getId().equals(dto.getId())) {
            throw ThrowableUtils.getBadRequestException();
        }

        item.setName(dto.getName());
    }
}
