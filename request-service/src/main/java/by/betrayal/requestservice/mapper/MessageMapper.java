package by.betrayal.requestservice.mapper;

import by.betrayal.requestservice.dto.message.CreateMessageDto;
import by.betrayal.requestservice.dto.message.MessagePreviewDto;
import by.betrayal.requestservice.dto.message.UpdateMessageDto;
import by.betrayal.requestservice.entity.MessageEntity;
import by.betrayal.requestservice.entity.TypeMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessagePreviewDto mapToDto(MessageEntity message);
    List<MessagePreviewDto> mapToDto(List<MessageEntity> messages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    MessageEntity mapToEntity(CreateMessageDto dto);

    default void mapToEntity(MessageEntity participant, UpdateMessageDto dto) {
        participant.setText(dto.getText());
        participant.setType(TypeMessage.USER);
    }
}
