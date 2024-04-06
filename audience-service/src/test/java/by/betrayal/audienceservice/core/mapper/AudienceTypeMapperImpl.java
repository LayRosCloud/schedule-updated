package by.betrayal.audienceservice.core.mapper;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeCreateDto;
import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.entity.AudienceTypeEntity;
import by.betrayal.audienceservice.mapper.AudienceTypeMapper;

import java.util.ArrayList;
import java.util.List;

public class AudienceTypeMapperImpl implements AudienceTypeMapper {

    @Override
    public List<AudienceTypeFullDto> mapToFullDto(List<AudienceTypeEntity> items) {
        if ( items == null ) {
            return null;
        }

        List<AudienceTypeFullDto> list = new ArrayList<AudienceTypeFullDto>( items.size() );
        for ( AudienceTypeEntity audienceTypeEntity : items ) {
            list.add( mapToFullDto( audienceTypeEntity ) );
        }

        return list;
    }

    @Override
    public AudienceTypeFullDto mapToFullDto(AudienceTypeEntity item) {
        if ( item == null ) {
            return null;
        }

        AudienceTypeFullDto audienceTypeFullDto = new AudienceTypeFullDto();

        audienceTypeFullDto.setId( item.getId() );
        audienceTypeFullDto.setName( item.getName() );

        return audienceTypeFullDto;
    }

    @Override
    public AudienceTypeEntity mapToEntity(AudienceTypeCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        AudienceTypeEntity audienceTypeEntity = new AudienceTypeEntity();

        audienceTypeEntity.setName( dto.getName() );

        return audienceTypeEntity;
    }
}
