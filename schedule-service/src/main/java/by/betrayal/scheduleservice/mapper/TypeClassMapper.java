package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.clazz.type.CreateTypeClassDto;
import by.betrayal.scheduleservice.dto.clazz.type.TypeClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.type.UpdateTypeClassDto;
import by.betrayal.scheduleservice.entity.TypeClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeClassMapper {

    TypeClassFullDto mapToDto(TypeClassEntity subject);
    List<TypeClassFullDto> mapToDto(List<TypeClassEntity> subject);

    @Mapping(target = "id", ignore = true)
    TypeClassEntity mapToEntity(CreateTypeClassDto dto);

    default void mapToEntity(TypeClassEntity subject, UpdateTypeClassDto dto) {
        subject.setName(dto.getName());
        subject.setInstitutionId(dto.getInstitutionId());
    }
}
