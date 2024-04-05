package by.betrayal.audienceservice.core.mapper;

import by.betrayal.audienceservice.dto.event.EventCreateDto;
import by.betrayal.audienceservice.dto.event.EventFullDto;
import by.betrayal.audienceservice.entity.EventEntity;
import by.betrayal.audienceservice.mapper.AudienceMapper;
import by.betrayal.audienceservice.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;

public class EventMapperImpl implements EventMapper {
    private final AudienceMapper audienceMapper = new AudienceMapperImpl();

    @Override
    public EventFullDto mapToFullDto(EventEntity event) {
        if ( event == null ) {
            return null;
        }

        EventFullDto eventFullDto = new EventFullDto();

        eventFullDto.setId( event.getId() );
        eventFullDto.setAudience( audienceMapper.mapToFullDto( event.getAudience() ) );
        eventFullDto.setValue( event.getValue() );
        eventFullDto.setDateStart( event.getDateStart() );
        eventFullDto.setDateEnd( event.getDateEnd() );

        return eventFullDto;
    }

    @Override
    public List<EventFullDto> mapToFullDto(List<EventEntity> events) {
        if ( events == null ) {
            return null;
        }

        List<EventFullDto> list = new ArrayList<EventFullDto>( events.size() );
        for ( EventEntity eventEntity : events ) {
            list.add( mapToFullDto( eventEntity ) );
        }

        return list;
    }

    @Override
    public EventEntity mapToEntity(EventCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        EventEntity eventEntity = new EventEntity();

        eventEntity.setValue( dto.getValue() );
        eventEntity.setDateStart( dto.getDateStart() );
        eventEntity.setDateEnd( dto.getDateEnd() );

        return eventEntity;
    }
}
