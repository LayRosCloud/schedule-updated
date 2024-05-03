package by.betrayal.groupservice.mapper;

import by.betrayal.groupservice.dto.group.CreateGroupDto;
import by.betrayal.groupservice.dto.group.GroupFullDto;
import by.betrayal.groupservice.dto.group.UpdateGroupDto;
import by.betrayal.groupservice.entity.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface GroupMapper {

    GroupFullDto mapToDto(GroupEntity group);
    List<GroupFullDto> mapToDto(List<GroupEntity> groups);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    GroupEntity mapToEntity(CreateGroupDto dto);
    default void mapToEntity(GroupEntity group, UpdateGroupDto dto) {
        group.setName(dto.getName());
    }
}
