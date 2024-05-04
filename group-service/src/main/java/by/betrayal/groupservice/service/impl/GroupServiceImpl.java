package by.betrayal.groupservice.service.impl;

import by.betrayal.groupservice.dto.group.CreateGroupDto;
import by.betrayal.groupservice.dto.group.GroupFullDto;
import by.betrayal.groupservice.dto.group.UpdateGroupDto;
import by.betrayal.groupservice.entity.CourseEntity;
import by.betrayal.groupservice.entity.GroupEntity;
import by.betrayal.groupservice.mapper.GroupMapper;
import by.betrayal.groupservice.repository.CourseRepository;
import by.betrayal.groupservice.repository.GroupRepository;
import by.betrayal.groupservice.service.GroupService;
import by.betrayal.groupservice.utils.ExceptionUtils;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableFactory;
import by.betrayal.groupservice.utils.pageable.PageableOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final GroupMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<GroupFullDto> findAllByCourse(Long courseId, PageableOptions options) {
        var course = findByIdCourseOrThrowNotFoundException(courseId);
        var pageable = PageableFactory.getPageableAsc(options);
        var groupPage = groupRepository.findAllByCourse(course, pageable);
        var groups = mapper.mapToDto(groupPage.getContent());
        return new PageableContainer<>(groupPage.getTotalElements(), groups);
    }

    @Override
    @Transactional(readOnly = true)
    public GroupFullDto findById(Long id) {
        var group = findByIdGroupOrThrowNotFoundException(id);
        return mapper.mapToDto(group);
    }

    @Override
    @Transactional
    public GroupFullDto create(CreateGroupDto dto) {
        var course = findByIdCourseOrThrowNotFoundException(dto.getCourseId());
        var group = mapper.mapToEntity(dto);
        group.setCourse(course);
        var result = groupRepository.save(group);
        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public List<GroupFullDto> create(List<CreateGroupDto> dtos) {
        var ids = dtos.stream().map(CreateGroupDto::getCourseId).toList();
        var courses = courseRepository.findAllByIdIn(ids);
        var hashCourses = courses.stream().collect(Collectors.toMap(CourseEntity::getId, obj -> obj));
        var list = new ArrayList<GroupEntity>();
        for (var dto: dtos) {
            var course = hashCourses.get(dto.getCourseId());
            if (course == null) {
                throw ExceptionUtils.getNotFoundIdException("Course", dto.getCourseId());
            }
            var group = mapper.mapToEntity(dto);
            group.setCourse(course);
            list.add(group);
        }
        var result = groupRepository.saveAll(list);
        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public GroupFullDto update(UpdateGroupDto dto) {
        var course = findByIdCourseOrThrowNotFoundException(dto.getCourseId());
        var group = findByIdGroupOrThrowNotFoundException(dto.getId());
        mapper.mapToEntity(group, dto);
        group.setCourse(course);
        var result = groupRepository.save(group);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public GroupFullDto delete(Long id) {
        var group = findByIdGroupOrThrowNotFoundException(id);
        groupRepository.delete(group);
        return mapper.mapToDto(group);
    }

    private CourseEntity findByIdCourseOrThrowNotFoundException(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Course", courseId)
        );
    }

    private GroupEntity findByIdGroupOrThrowNotFoundException(Long id) {
        return groupRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Group", id)
        );
    }
}
