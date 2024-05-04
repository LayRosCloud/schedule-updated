package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.clazz.ClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.CreateClassDto;
import by.betrayal.scheduleservice.dto.clazz.UpdateClassDto;
import by.betrayal.scheduleservice.entity.ClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassMapper {

    List<ClassFullDto> mapToDto(List<ClassEntity> classes);

    ClassFullDto mapToDto(ClassEntity clazz);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacherSubject", ignore = true)
    @Mapping(target = "type", ignore = true)
    @Mapping(target = "subgroup", ignore = true)
    @Mapping(target = "time", ignore = true)
    ClassEntity mapToEntity(CreateClassDto dto);

    default void mapToEntity(ClassEntity clazz, UpdateClassDto dto) {
        clazz.setAudienceId(dto.getAudienceId());
        clazz.setDateStart(dto.getDateStart());
        clazz.setDateEnd(dto.getDateEnd());
    }
}
