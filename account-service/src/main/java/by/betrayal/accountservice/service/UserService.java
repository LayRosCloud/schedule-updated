package by.betrayal.accountservice.service;

import by.betrayal.accountservice.dto.token.JwtAccessDto;
import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.dto.user.UserCreateDto;
import by.betrayal.accountservice.dto.user.UserFullDto;
import by.betrayal.accountservice.dto.user.UserLoginDto;
import by.betrayal.accountservice.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {

    List<UserFullDto> findAll();
    UserFullDto findById(Long id);
    UserFullDto create(UserCreateDto dto);
    JwtFullDto login(UserLoginDto dto);
    JwtFullDto refresh(RefreshAccessTokenDto dto);
    void logout(RefreshAccessTokenDto dto);
    UserFullDto update(UserUpdateDto dto);
    UserFullDto delete(Long id);
}
