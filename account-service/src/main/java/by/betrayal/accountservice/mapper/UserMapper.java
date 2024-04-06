package by.betrayal.accountservice.mapper;

import by.betrayal.accountservice.dto.user.UserCreateDto;
import by.betrayal.accountservice.dto.user.UserFullDto;
import by.betrayal.accountservice.dto.user.UserUpdateDto;
import by.betrayal.accountservice.entity.UserEntity;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserFullDto mapToDto(UserEntity user);
    List<UserFullDto> mapToListDto(List<UserEntity> users);
    UserEntity mapToEntity(UserCreateDto dto);
    default void mapToEntity(UserEntity entity, UserUpdateDto dto) {
        entity.setPersonId(dto.getPersonId());
        entity.setIsActivated(dto.getIsActivated());
        entity.setIsBanned(dto.getIsBanned());
        entity.setCreatedAt(Instant.now().getEpochSecond());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
    }

}
