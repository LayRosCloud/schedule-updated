package by.betrayal.audienceservice.core.mapper;

import by.betrayal.audienceservice.dto.institution.InstitutionCreateDto;
import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import by.betrayal.audienceservice.entity.InstitutionEntity;
import by.betrayal.audienceservice.mapper.InstitutionMapper;

import java.util.ArrayList;
import java.util.List;

public class InstitutionMapperImpl implements InstitutionMapper {
    @Override
    public InstitutionFullDto mapToFullDto(InstitutionEntity item) {
        if ( item == null ) {
            return null;
        }

        InstitutionFullDto institutionFullDto = new InstitutionFullDto();

        institutionFullDto.setId( item.getId() );
        institutionFullDto.setName( item.getName() );

        return institutionFullDto;
    }

    @Override
    public List<InstitutionFullDto> mapToFullDto(List<InstitutionEntity> item) {
        if ( item == null ) {
            return null;
        }

        List<InstitutionFullDto> list = new ArrayList<InstitutionFullDto>( item.size() );
        for ( InstitutionEntity institutionEntity : item ) {
            list.add( mapToFullDto( institutionEntity ) );
        }

        return list;
    }

    @Override
    public InstitutionEntity mapToEntity(InstitutionCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        InstitutionEntity institutionEntity = new InstitutionEntity();

        institutionEntity.setName( dto.getName() );

        return institutionEntity;
    }
}
