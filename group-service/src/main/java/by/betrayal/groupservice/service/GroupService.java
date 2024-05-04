package by.betrayal.groupservice.service;

import by.betrayal.groupservice.dto.group.CreateGroupDto;
import by.betrayal.groupservice.dto.group.GroupFullDto;
import by.betrayal.groupservice.dto.group.UpdateGroupDto;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;

import java.util.List;

public interface GroupService {

    PageableContainer<GroupFullDto> findAllByCourse(Long courseId, PageableOptions options);
    GroupFullDto findById(Long id);
    GroupFullDto create(CreateGroupDto dto);
    List<GroupFullDto> create(List<CreateGroupDto> dto);
    GroupFullDto update(UpdateGroupDto dto);
    GroupFullDto delete(Long id);
}
