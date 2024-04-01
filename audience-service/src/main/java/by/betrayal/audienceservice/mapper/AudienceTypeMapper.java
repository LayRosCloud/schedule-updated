package by.betrayal.audienceservice.mapper;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceUpdateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeUpdateDto;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AudienceTypeMapper {

    List<AudienceTypeFullDto> mapToFullDto(List<AudienceTypeEntity> items);
    AudienceTypeFullDto mapToFullDto(AudienceTypeEntity item);

    @Mapping(target = "id", ignore = true)
    AudienceTypeEntity mapToEntity(AudienceTypeCreateDto dto);

    default void mapToEntity(AudienceTypeEntity item, AudienceTypeUpdateDto dto) {
        if (item == null || dto == null) {
            throw ThrowableUtils.getBadRequestException();
        }

        if (!item.getId().equals(dto.getId())) {
            throw ThrowableUtils.getBadRequestException();
        }

        item.setName(dto.getName());
    }
}
