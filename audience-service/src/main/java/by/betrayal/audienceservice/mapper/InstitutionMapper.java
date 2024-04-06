package by.betrayal.audienceservice.mapper;

import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.dto.institution.InstitutionUpdateDto;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {
    InstitutionFullDto mapToFullDto(InstitutionEntity item);
    List<InstitutionFullDto> mapToFullDto(List<InstitutionEntity> item);
    @Mapping(target = "id", ignore = true)
    InstitutionEntity mapToEntity(InstitutionCreateDto dto);

    default void mapToEntity(InstitutionEntity item, InstitutionUpdateDto dto) {
        if (item == null || dto == null) {
            throw ThrowableUtils.getBadRequestException();
        }

        if (!item.getId().equals(dto.getId())) {
            throw ThrowableUtils.getBadRequestException();
        }

        item.setName(dto.getName());
    }
}
