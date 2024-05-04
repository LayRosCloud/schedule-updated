package by.betrayal.groupservice.mapper;

import by.betrayal.groupservice.dto.event.CreateEventDto;
import by.betrayal.groupservice.dto.event.EventFullDto;
import by.betrayal.groupservice.dto.event.UpdateEventDto;
import by.betrayal.groupservice.entity.EventEntity;
import by.betrayal.groupservice.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GroupMapper.class})
public interface EventMapper {
    EventFullDto mapToDto(EventEntity event);
    List<EventFullDto> mapToDto(List<EventEntity> events);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateStart", ignore = true)
    @Mapping(target = "dateEnd", ignore = true)
    @Mapping(target = "group", ignore = true)
    EventEntity mapToEntity(CreateEventDto event);

    default void mapToEntity(EventEntity event, UpdateEventDto dto) {
        event.setValue(dto.getValue());
        event.setDateStart(DateUtils.getTicks(dto.getDateStart()));

        if (dto.getDateEnd() != null) {
            event.setDateEnd(DateUtils.getTicks(dto.getDateEnd()));
        }
    }
}
