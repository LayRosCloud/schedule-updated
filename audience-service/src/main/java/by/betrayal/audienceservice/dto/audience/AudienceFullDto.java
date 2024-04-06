package by.betrayal.audienceservice.dto.audience;

import by.betrayal.audienceservice.dto.audiencetype.AudienceTypeFullDto;
import by.betrayal.audienceservice.dto.corpus.CorpusFullDto;
import lombok.Data;

@Data
public class AudienceFullDto {
    private Long id;
    private String name;
    private CorpusFullDto corpus;
    private AudienceTypeFullDto type;
}
