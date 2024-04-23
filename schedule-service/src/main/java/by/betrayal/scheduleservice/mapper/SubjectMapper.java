package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.subject.CreateSubjectDto;
import by.betrayal.scheduleservice.dto.subject.SubjectFullDto;
import by.betrayal.scheduleservice.dto.subject.UpdateSubjectDto;
import by.betrayal.scheduleservice.entity.SubjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectFullDto mapToDto(SubjectEntity subject);
    List<SubjectFullDto> mapToDto(List<SubjectEntity> subject);

    @Mapping(target = "id", ignore = true)
    SubjectEntity mapToEntity(CreateSubjectDto dto);

    default void mapToEntity(SubjectEntity subject, UpdateSubjectDto dto) {
        subject.setName(dto.getName());
        subject.setLongName(dto.getLongName());
        subject.setInstitutionId(dto.getInstitutionId());
    }
}
