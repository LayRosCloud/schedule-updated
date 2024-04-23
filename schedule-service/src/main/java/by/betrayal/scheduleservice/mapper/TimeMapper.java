package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.time.CreateTimeDto;
import by.betrayal.scheduleservice.dto.time.TimeFullDto;
import by.betrayal.scheduleservice.entity.TimeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeMapper {

    TimeFullDto mapToDto(TimeEntity time);
    List<TimeFullDto> mapToDto(List<TimeEntity> times);
    @Mapping(target = "id", ignore = true)
    TimeEntity mapToEntity(CreateTimeDto dto);
}
