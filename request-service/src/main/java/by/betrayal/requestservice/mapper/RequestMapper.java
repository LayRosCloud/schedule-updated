package by.betrayal.requestservice.mapper;

import by.betrayal.requestservice.dto.request.CreateRequestDto;
import by.betrayal.requestservice.dto.request.RequestFullDto;
import by.betrayal.requestservice.dto.request.UpdateRequestDto;
import by.betrayal.requestservice.entity.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    List<RequestFullDto> mapToDto(List<RequestEntity> items);
    RequestFullDto mapToDto(RequestEntity item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    RequestEntity mapToEntity(CreateRequestDto item);

    default void mapToEntity(RequestEntity item, UpdateRequestDto dto) {
        item.setTheme(dto.getTheme());
        item.setApproved(dto.getApproved());
    }

}
