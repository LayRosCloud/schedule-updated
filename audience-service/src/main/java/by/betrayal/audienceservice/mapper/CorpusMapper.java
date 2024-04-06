package by.betrayal.audienceservice.mapper;

import by.betrayal.audienceservice.dto.corpus.CorpusCreateDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusUpdateDto;
import by.betrayal.audienceservice.entity.CorpusEntity;
import by.betrayal.audienceservice.utils.ThrowableUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InstitutionMapper.class})
public interface CorpusMapper {

    CorpusFullDto mapToFullDto(CorpusEntity corpus);
    List<CorpusFullDto> mapToFullDto(List<CorpusEntity> items);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution", ignore = true)
    CorpusEntity mapToEntity(CorpusCreateDto items);

    default void mapToEntity(CorpusEntity item, CorpusUpdateDto dto) {
        if (item == null || dto == null) {
            throw ThrowableUtils.getBadRequestException();
        }

        if (!item.getId().equals(dto.getId())) {
            throw ThrowableUtils.getBadRequestException();
        }

        item.setName(dto.getName());
        item.setAddress(dto.getAddress());
    }
}
