package by.betrayal.scheduleservice.service.impl;

import by.betrayal.scheduleservice.dto.clazz.ClassFullDto;
import by.betrayal.scheduleservice.dto.clazz.CreateClassDto;
import by.betrayal.scheduleservice.dto.clazz.UpdateClassDto;
import by.betrayal.scheduleservice.entity.*;
import by.betrayal.scheduleservice.mapper.ClassMapper;
import by.betrayal.scheduleservice.repository.*;
import by.betrayal.scheduleservice.service.ClassService;
import by.betrayal.scheduleservice.utils.ClassRangeOptions;
import by.betrayal.scheduleservice.utils.ThrowableUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final SubgroupRepository subgroupRepository;
    private final TimeRepository timeRepository;
    private final TypeClassRepository typeClassRepository;
    private final ClassMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClassFullDto> findAllByAudienceId(Long audienceId, Date start, Date end) {
        var list = classRepository.findAllByAudienceId(audienceId, start, end);
        return mapper.mapToDto(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassFullDto> findAllByTeacherId(Long teacherId, Date start, Date end) {
        var list = classRepository.findAllByTeacherId(teacherId, start, end);
        return mapper.mapToDto(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassFullDto> findAllBySubgroupId(Long subgroupId, Date start, Date end) {
        var list = classRepository.findAllBySubgroupId(subgroupId, start, end);
        return mapper.mapToDto(list);
    }

    @Override
    @Transactional(readOnly = true)
    public ClassFullDto findById(Long id) {
        var item = findByIdOrThrowNotFoundException(id);
        return mapper.mapToDto(item);
    }

    @Override
    public ClassFullDto create(CreateClassDto dto) {
        var dateStart = dto.getDateStart();
        var dateEnd = dto.getDateEnd();
        if (isAfter(dateStart, dateEnd)) {
            throwDateException();
        }
        var type = findByIdTypeOrThrowNotFoundException(dto.getTypeId());
        var subgroup = findByIdSubgroupOrThrowNotFoundException(dto.getSubgroupId());
        var teacherSubject = findByIdTeacherSubjectOrThrowNotFoundException(dto.getTeacherSubjectId());
        var time = findByIdTimeOrThrowNotFoundException(dto.getTimeId());

        var clazz = mapper.mapToEntity(dto);

        clazz.setType(type);
        clazz.setSubgroup(subgroup);
        clazz.setTeacherSubject(teacherSubject);
        clazz.setTime(time);

        var result = classRepository.save(clazz);

        return mapper.mapToDto(result);
    }


    @Override
    @Transactional
    public List<ClassFullDto> createRange(List<CreateClassDto> dtos) {
        dtos.forEach(dto -> {
            var dateStart = dto.getDateStart();
            var dateEnd = dto.getDateEnd();
            if (isAfter(dateStart, dateEnd)) {
                throwDateException();
            }
        });
        var types = getLongTypeHashtable(dtos);
        var times = getLongTimeHashtable(dtos);
        var teacherSubjects = getLongTeacherSubjectHashtable(dtos);
        var subgroups = getLongSubgroupHashtable(dtos);

        var options = ClassRangeOptions.builder()
                .types(types)
                .times(times)
                .teacherSubjects(teacherSubjects)
                .subgroups(subgroups)
                .dtos(dtos)
                .build();

        var classes = convertDtosToEntities(options);

        var result = classRepository.saveAll(classes);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public ClassFullDto update(UpdateClassDto dto) {
        var dateStart = dto.getDateStart();
        var dateEnd = dto.getDateEnd();
        if (isAfter(dateStart, dateEnd)) {
            throwDateException();
        }

        var clazz = findByIdOrThrowNotFoundException(dto.getId());

        var type = findByIdTypeOrThrowNotFoundException(dto.getTypeId());
        clazz.setType(type);

        var subgroup = findByIdSubgroupOrThrowNotFoundException(dto.getSubgroupId());
        clazz.setSubgroup(subgroup);

        var teacherSubject = findByIdTeacherSubjectOrThrowNotFoundException(dto.getTeacherSubjectId());
        clazz.setTeacherSubject(teacherSubject);

        var time = findByIdTimeOrThrowNotFoundException(dto.getTimeId());
        clazz.setTime(time);

        var result = classRepository.save(clazz);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public ClassFullDto delete(Long id) {
        var clazz = findByIdOrThrowNotFoundException(id);
        classRepository.delete(clazz);
        return mapper.mapToDto(clazz);
    }

    private static boolean isAfter(Date dateStart, Date dateEnd) {
        return dateStart.after(dateEnd);
    }

    private ClassEntity findByIdOrThrowNotFoundException(Long id) {
        return classRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Class with id %s is not found", id)
        );
    }

    private static void throwDateException() {
        throw ThrowableUtils.getBadRequestException("date start was longer date end");
    }

    private TypeClassEntity findByIdTypeOrThrowNotFoundException(Long id) {
        return typeClassRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Type with id %s is not found", id)
        );
    }

    private SubgroupEntity findByIdSubgroupOrThrowNotFoundException(Long id) {
        return subgroupRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Subgroup with id %s is not found", id)
        );
    }

    private TeacherSubjectEntity findByIdTeacherSubjectOrThrowNotFoundException(Long id) {
        return teacherSubjectRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Teacher Subject with id %s is not found", id)
        );
    }

    private TimeEntity findByIdTimeOrThrowNotFoundException(Long id) {
        return timeRepository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("Time with id %s is not found", id)
        );
    }

    private Hashtable<Long, TimeEntity> findByIdTimeIn(Collection<Long> ids) {
        var list = timeRepository.findAllByIdIn(ids);
        var hashtable = new Hashtable<Long, TimeEntity>();

        for (var time : list) {
            hashtable.put(time.getId(), time);
        }

        return hashtable;
    }

    private Hashtable<Long, SubgroupEntity> findByIdSubgroupIn(Collection<Long> ids) {
        var list = subgroupRepository.findAllByIdIn(ids);
        var hashtable = new Hashtable<Long, SubgroupEntity>();

        for (var subgroup : list) {
            hashtable.put(subgroup.getId(), subgroup);
        }

        return hashtable;
    }

    private Hashtable<Long, TypeClassEntity> findByIdTypeIn(Collection<Long> ids) {
        var list = typeClassRepository.findAllByIdIn(ids);
        var hashtable = new Hashtable<Long, TypeClassEntity>();

        for (var type : list) {
            hashtable.put(type.getId(), type);
        }

        return hashtable;
    }

    private Hashtable<Long, TeacherSubjectEntity> findByIdTeacherSubjectIn(Collection<Long> ids) {
        var list = teacherSubjectRepository.findAllByIdIn(ids);
        var hashtable = new Hashtable<Long, TeacherSubjectEntity>();

        for (var teacherSubject : list) {
            hashtable.put(teacherSubject.getId(), teacherSubject);
        }

        return hashtable;
    }

    private List<ClassEntity> convertDtosToEntities(ClassRangeOptions options) {
        var list = new ArrayList<ClassEntity>(options.getDtos().size());
        for (var dto : options.getDtos()) {
            var subgroup = getSubgroup(options.getSubgroups(), dto);
            var teacherSubject = getTeacherSubject(options.getTeacherSubjects(), dto);
            var time = getTime(options.getTimes(), dto);
            var type = getType(options.getTypes(), dto);

            var clazz = mapper.mapToEntity(dto);

            clazz.setTeacherSubject(teacherSubject);
            clazz.setSubgroup(subgroup);
            clazz.setTime(time);
            clazz.setType(type);
            list.add(clazz);
        }

        return list;
    }

    private static TypeClassEntity getType(Hashtable<Long, TypeClassEntity> types, CreateClassDto dto) {
        var type = types.get(dto.getTypeId());

        if (type == null) {
            throw ThrowableUtils.getNotFoundException("Type with id %s is not found", dto.getTypeId());
        }

        return type;
    }

    private static TimeEntity getTime(Hashtable<Long, TimeEntity> times, CreateClassDto dto) {
        var time = times.get(dto.getTimeId());

        if (time == null) {
            throw ThrowableUtils.getNotFoundException("Time with id %s is not found", dto.getTimeId());
        }

        return time;
    }

    private static TeacherSubjectEntity getTeacherSubject(Hashtable<Long, TeacherSubjectEntity> teacherSubjects, CreateClassDto dto) {
        var teacherSubject = teacherSubjects.get(dto.getTeacherSubjectId());

        if (teacherSubject == null) {
            throw ThrowableUtils.getNotFoundException("TeacherSubject with id %s is not found", dto.getTeacherSubjectId());
        }

        return teacherSubject;
    }

    private static SubgroupEntity getSubgroup(Hashtable<Long, SubgroupEntity> subgroups, CreateClassDto dto) {
        var subgroup = subgroups.get(dto.getSubgroupId());

        if (subgroup == null) {
            throw ThrowableUtils.getNotFoundException("Subgroup with id %s is not found", dto.getSubgroupId());
        }

        return subgroup;
    }

    private Hashtable<Long, SubgroupEntity> getLongSubgroupHashtable(List<CreateClassDto> dtos) {
        var subgroupIds = dtos.stream().map(CreateClassDto::getSubgroupId).collect(Collectors.toList());
        return findByIdSubgroupIn(subgroupIds);
    }

    private Hashtable<Long, TeacherSubjectEntity> getLongTeacherSubjectHashtable(List<CreateClassDto> dtos) {
        var teacherSubjectIds = dtos.stream().map(CreateClassDto::getTeacherSubjectId).collect(Collectors.toList());
        return findByIdTeacherSubjectIn(teacherSubjectIds);
    }

    private Hashtable<Long, TimeEntity> getLongTimeHashtable(List<CreateClassDto> dtos) {
        var timeIds = dtos.stream().map(CreateClassDto::getTimeId).collect(Collectors.toList());
        return findByIdTimeIn(timeIds);
    }

    private Hashtable<Long, TypeClassEntity> getLongTypeHashtable(List<CreateClassDto> dtos) {
        var typeIds = dtos.stream().map(CreateClassDto::getTypeId).collect(Collectors.toList());
        return findByIdTypeIn(typeIds);
    }
}
