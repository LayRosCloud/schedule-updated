package by.betrayal.groupservice.mapper;

import by.betrayal.groupservice.dto.faculty.CreateFacultyDto;
import by.betrayal.groupservice.dto.faculty.FacultyFullDto;
import by.betrayal.groupservice.dto.faculty.UpdateFacultyDto;
import by.betrayal.groupservice.entity.FacultyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    FacultyFullDto mapToDto(FacultyEntity faculty);
    List<FacultyFullDto> mapToDto(List<FacultyEntity> faculty);
    @Mapping(target = "id", ignore = true)
    FacultyEntity mapToEntity(CreateFacultyDto dto);
    default void mapToEntity(FacultyEntity faculty, UpdateFacultyDto dto) {
        faculty.setName(dto.getName());
        faculty.setCorpusId(dto.getCorpusId());
    }
}
