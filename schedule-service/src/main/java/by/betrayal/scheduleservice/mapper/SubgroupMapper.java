package by.betrayal.scheduleservice.mapper;

import by.betrayal.scheduleservice.dto.subgroup.CreateSubgroupDto;
import by.betrayal.scheduleservice.dto.subgroup.SubgroupFullDto;
import by.betrayal.scheduleservice.dto.subgroup.UpdateSubgroupDto;
import by.betrayal.scheduleservice.entity.SubgroupEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubgroupMapper {
    SubgroupFullDto mapToDto(SubgroupEntity subgroup);
    List<SubgroupFullDto> mapToDto(List<SubgroupEntity> subgroup);
    SubgroupEntity mapToEntity(CreateSubgroupDto dto);
    default void mapToEntity(SubgroupEntity subgroup, UpdateSubgroupDto dto) {
        subgroup.setName(dto.getName());
        subgroup.setGroupId(dto.getGroupId());
    }
}
