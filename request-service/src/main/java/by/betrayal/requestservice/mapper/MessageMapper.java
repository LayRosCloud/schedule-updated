package by.betrayal.requestservice.mapper;

import by.betrayal.requestservice.dto.message.CreateMessageDto;
import by.betrayal.requestservice.dto.message.MessageFullDto;
import by.betrayal.requestservice.dto.message.MessagePreviewDto;
import by.betrayal.requestservice.dto.message.UpdateMessageDto;
import by.betrayal.requestservice.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ParticipantMapper.class })
public interface MessageMapper {

    MessagePreviewDto mapToDto(MessageEntity message);
    List<MessagePreviewDto> mapToDto(List<MessageEntity> messages);

    MessageFullDto mapToFullDto(MessageEntity message);
    List<MessageFullDto> mapToFullDto(List<MessageEntity> messages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MessageEntity mapToEntity(CreateMessageDto dto);

    default void mapToEntity(MessageEntity message, UpdateMessageDto dto) {
        message.setText(dto.getText());
    }
}
