package by.betrayal.audienceservice.core.mapper;

import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.mapper.CorpusMapper;
import by.betrayal.audienceservice.mapper.InstitutionMapper;

import java.util.ArrayList;
import java.util.List;

public class CorpusMapperImpl implements CorpusMapper {

    private final InstitutionMapper institutionMapper = new InstitutionMapperImpl();

    @Override
    public CorpusFullDto mapToFullDto(CorpusEntity corpus) {
        if ( corpus == null ) {
            return null;
        }

        CorpusFullDto corpusFullDto = new CorpusFullDto();

        corpusFullDto.setId( corpus.getId() );
        corpusFullDto.setName( corpus.getName() );
        corpusFullDto.setAddress( corpus.getAddress() );
        corpusFullDto.setInstitution( institutionMapper.mapToFullDto( corpus.getInstitution() ) );

        return corpusFullDto;
    }

    @Override
    public List<CorpusFullDto> mapToFullDto(List<CorpusEntity> items) {
        if ( items == null ) {
            return null;
        }

        List<CorpusFullDto> list = new ArrayList<CorpusFullDto>( items.size() );
        for ( CorpusEntity corpusEntity : items ) {
            list.add( mapToFullDto( corpusEntity ) );
        }

        return list;
    }

    @Override
    public CorpusEntity mapToEntity(CorpusCreateDto items) {
        if ( items == null ) {
            return null;
        }

        CorpusEntity corpusEntity = new CorpusEntity();

        corpusEntity.setName( items.getName() );
        corpusEntity.setAddress( items.getAddress() );

        return corpusEntity;
    }
}
