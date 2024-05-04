package by.betrayal.groupservice.service.impl;

import by.betrayal.groupservice.dto.course.CourseFullDto;
import by.betrayal.groupservice.dto.course.CreateCourseDto;
import by.betrayal.groupservice.dto.course.UpdateCourseDto;
import by.betrayal.groupservice.entity.CourseEntity;
import by.betrayal.groupservice.entity.FacultyEntity;
import by.betrayal.groupservice.mapper.CourseMapper;
import by.betrayal.groupservice.repository.CourseRepository;
import by.betrayal.groupservice.repository.FacultyRepository;
import by.betrayal.groupservice.service.CourseService;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;
    private final CourseMapper courseMapper;

    @Override
    @Transactional(readOnly = true)
    public PageableContainer<CourseFullDto> findAllByFaculty(Long facultyId, PageableOptions options) {
        var pageable = PageableFactory.getPageableAsc(options);
        var faculty = findByIdFacultyOrThrowNotFoundException(facultyId);
        var coursePage = courseRepository.findAllByFaculty(faculty, pageable);
        var courses = courseMapper.mapToDto(coursePage.getContent());
        return new PageableContainer<>(coursePage.getTotalElements(), courses);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseFullDto findById(Long id) {
        var course = findByIdCourseOrThrowNotFoundException(id);
        return courseMapper.mapToDto(course);
    }

    @Override
    @Transactional
    public CourseFullDto create(CreateCourseDto dto) {
        var faculty = findByIdFacultyOrThrowNotFoundException(dto.getFacultyId());
        var course = courseMapper.mapToEntity(dto);
        course.setFaculty(faculty);
        var savedCourse = courseRepository.save(course);
        return courseMapper.mapToDto(savedCourse);
    }

    @Override
    @Transactional
    public List<CourseFullDto> create(List<CreateCourseDto> dtos) {
        var ids = dtos.stream().map(CreateCourseDto::getFacultyId).toList();
        var faculties = facultyRepository.findAllByIdIn(ids);
        var hashFaculties = faculties.stream().collect(Collectors.toMap(FacultyEntity::getId, obj -> obj));

        var list = new ArrayList<CourseEntity>();

        for (var dto: dtos) {
            var faculty = hashFaculties.get(dto.getFacultyId());
            if (faculty == null) {
                throw ExceptionUtils.getNotFoundIdException("Faculty", dto.getFacultyId());
            }
            var course = courseMapper.mapToEntity(dto);
            course.setFaculty(faculty);
            list.add(course);
        }
        var result = courseRepository.saveAll(list);

        return courseMapper.mapToDto(result);
    }

    @Override
    @Transactional
    public CourseFullDto update(UpdateCourseDto dto) {
        var faculty = findByIdFacultyOrThrowNotFoundException(dto.getFacultyId());
        var course = findByIdCourseOrThrowNotFoundException(dto.getId());
        courseMapper.mapToEntity(course, dto);
        course.setFaculty(faculty);
        var savedCourse = courseRepository.save(course);
        return courseMapper.mapToDto(savedCourse);
    }

    @Override
    @Transactional
    public CourseFullDto delete(Long id) {
        var course = findByIdCourseOrThrowNotFoundException(id);
        courseRepository.delete(course);
        return courseMapper.mapToDto(course);
    }

    private FacultyEntity findByIdFacultyOrThrowNotFoundException(Long facultyId) {
        return facultyRepository.findById(facultyId).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Faculty", facultyId)
        );
    }

    private CourseEntity findByIdCourseOrThrowNotFoundException(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                ExceptionUtils.getNotFoundIdException("Course", id)
        );
    }
}
