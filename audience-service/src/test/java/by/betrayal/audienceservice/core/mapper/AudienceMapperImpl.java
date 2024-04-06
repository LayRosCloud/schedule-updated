package by.betrayal.audienceservice.core.mapper;

import by.betrayal.audienceservice.dto.audience.AudienceCreateDto;
import by.betrayal.audienceservice.dto.audience.AudienceFullDto;
import by.betrayal.audienceservice.entity.AudienceEntity;
import by.betrayal.audienceservice.mapper.AudienceMapper;
import by.betrayal.audienceservice.mapper.AudienceTypeMapper;
import by.betrayal.audienceservice.mapper.CorpusMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AudienceMapperImpl implements AudienceMapper {

    private final CorpusMapper corpusMapper = new CorpusMapperImpl();
    private final AudienceTypeMapper audienceTypeMapper = new AudienceTypeMapperImpl();

    @Override
    public List<AudienceFullDto> mapToFullDto(List<AudienceEntity> items) {
        if ( items == null ) {
            return null;
        }

        List<AudienceFullDto> list = new ArrayList<AudienceFullDto>( items.size() );
        for ( AudienceEntity audienceEntity : items ) {
            list.add( mapToFullDto( audienceEntity ) );
        }

        return list;
    }

    @Override
    public AudienceFullDto mapToFullDto(AudienceEntity item) {
        if ( item == null ) {
            return null;
        }

        AudienceFullDto audienceFullDto = new AudienceFullDto();

        audienceFullDto.setId( item.getId() );
        audienceFullDto.setName( item.getName() );
        audienceFullDto.setCorpus( corpusMapper.mapToFullDto( item.getCorpus() ) );
        audienceFullDto.setType( audienceTypeMapper.mapToFullDto( item.getType() ) );

        return audienceFullDto;
    }

    @Override
    public AudienceEntity mapToEntity(AudienceCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        AudienceEntity audienceEntity = new AudienceEntity();

        audienceEntity.setName( dto.getName() );

        return audienceEntity;
    }
}
