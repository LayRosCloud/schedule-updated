package by.betrayal.audienceservice.dto.corpus;

import by.betrayal.audienceservice.dto.institution.InstitutionFullDto;
import lombok.Data;

@Data
public class CorpusFullDto {
    private Long id;
    private String name;
    private String address;
    private InstitutionFullDto institution;
}
