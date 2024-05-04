package by.betrayal.requestservice.service;

import by.betrayal.requestservice.dto.request.CreateRequestDto;
import by.betrayal.requestservice.dto.request.RequestFullDto;
import by.betrayal.requestservice.dto.request.UpdateRequestDto;

public interface RequestService {

    RequestFullDto findById(Long id);
    RequestFullDto create(CreateRequestDto dto);
    RequestFullDto update(UpdateRequestDto dto);
    RequestFullDto delete(Long id);
}
