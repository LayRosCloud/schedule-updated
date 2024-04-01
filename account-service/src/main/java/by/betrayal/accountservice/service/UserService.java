package by.betrayal.accountservice.service;

import by.betrayal.accountservice.dto.token.JwtAccessDto;
import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.dto.user.UserCreateDto;
import by.betrayal.accountservice.dto.user.UserFullDto;
import by.betrayal.accountservice.dto.user.UserLoginDto;
import by.betrayal.accountservice.dto.user.UserUpdateDto;
import by.betrayal.accountservice.utils.pageable.PageableOptions;

import java.util.List;

public interface UserService {

    List<UserFullDto> findAll(PageableOptions options);
    UserFullDto findById(Long id);
    UserFullDto create(UserCreateDto dto);
    JwtFullDto login(UserLoginDto dto);
    JwtFullDto refresh(RefreshAccessTokenDto dto);
    void logout(RefreshAccessTokenDto dto);
    UserFullDto update(UserUpdateDto dto);
    UserFullDto delete(Long id);
}
