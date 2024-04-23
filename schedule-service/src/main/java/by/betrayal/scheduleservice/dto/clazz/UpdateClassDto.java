package by.betrayal.scheduleservice.dto.clazz;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UpdateClassDto {

    private Long id;
    private Date dateStart;
    private Date dateEnd;
    private Long audienceId;
    private Long teacherSubjectId;
    private Long subgroupId;
    private Long timeId;
    private Long typeId;
}
