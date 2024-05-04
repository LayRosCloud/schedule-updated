package by.betrayal.groupservice.service;

import by.betrayal.groupservice.dto.course.CourseFullDto;
import by.betrayal.groupservice.dto.course.CreateCourseDto;
import by.betrayal.groupservice.dto.course.UpdateCourseDto;
import by.betrayal.groupservice.utils.pageable.PageableContainer;
import by.betrayal.groupservice.utils.pageable.PageableOptions;

import java.util.List;

public interface CourseService {

    PageableContainer<CourseFullDto> findAllByFaculty(Long facultyId, PageableOptions options);
    CourseFullDto findById(Long id);
    CourseFullDto create(CreateCourseDto dto);
    List<CourseFullDto> create(List<CreateCourseDto> dtos);
    CourseFullDto update(UpdateCourseDto dto);
    CourseFullDto delete(Long id);

}
