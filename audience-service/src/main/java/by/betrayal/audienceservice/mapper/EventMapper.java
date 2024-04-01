package by.betrayal.audienceservice.mapper;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.dto.event.EventUpdateDto;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AudienceMapper.class})
public interface EventMapper {
    EventFullDto mapToFullDto(EventEntity event);
    List<EventFullDto> mapToFullDto(List<EventEntity> events);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "audience", ignore = true)
    EventEntity mapToEntity(EventCreateDto dto);

    default void mapToEntity(EventEntity item, EventUpdateDto dto) {
        if (item == null || dto == null) {
            throw ThrowableUtils.getBadRequestException();
        }

        if (!item.getId().equals(dto.getId())) {
            throw ThrowableUtils.getBadRequestException();
        }

        item.setValue(dto.getValue());
        item.setDateEnd(dto.getDateEnd());
        item.setDateStart(dto.getDateStart());
    }
}
