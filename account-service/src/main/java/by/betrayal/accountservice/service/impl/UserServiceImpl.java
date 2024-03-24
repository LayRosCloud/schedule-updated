package by.betrayal.accountservice.service.impl;

import by.betrayal.accountservice.dto.token.JwtAccessDto;
import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.dto.user.UserCreateDto;
import by.betrayal.accountservice.dto.user.UserFullDto;
import by.betrayal.accountservice.dto.user.UserLoginDto;
import by.betrayal.accountservice.dto.user.UserUpdateDto;
import by.betrayal.accountservice.entity.PermissionEntity;
import by.betrayal.accountservice.entity.Scope;
import by.betrayal.accountservice.entity.UserEntity;
import by.betrayal.accountservice.exception.BadRequestException;
import by.betrayal.accountservice.exception.NotFoundException;
import by.betrayal.accountservice.mapper.UserMapper;
import by.betrayal.accountservice.repository.PermissionRepository;
import by.betrayal.accountservice.repository.UserRepository;
import by.betrayal.accountservice.service.TokenService;
import by.betrayal.accountservice.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final PermissionRepository permissionRepository;
    private final TokenService tokenService;

    @Override
    @Transactional
    public List<UserFullDto> findAll() {
        var users = repository.findAll();

        return mapper.mapToListDto(users);
    }

    @Override
    @Transactional
    public UserFullDto findById(Long id) {
        var user = repository.findById(id).orElseThrow(() ->
                new NotFoundException()
        );

        return mapper.mapToDto(user);
    }

    @Override
    @Transactional
    public UserFullDto create(UserCreateDto dto) {
        var user = repository.findByEmailOrLogin(dto.getEmail(), dto.getLogin());
        if (user.isPresent()) {
            throw new BadRequestException();
        }

        var item = mapper.mapToEntity(dto);
        item.setCreatedAt(System.currentTimeMillis());
        item.setPassword(encoder.encode(dto.getPassword()));
        item.setIsBanned(false);
        item.setIsActivated(false);
        var result = repository.save(item);

        Set<PermissionEntity> scopeSet = new HashSet<>();

        for (String scopeString : dto.getScopes()) {
            var scope = Scope.valueOf(scopeString);
            var permission = new PermissionEntity();
            permission.setScope(scope);
            permission.setUser(result);
            scopeSet.add(permission);
        }

        permissionRepository.saveAll(scopeSet);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public JwtFullDto login(UserLoginDto dto) {
        UserEntity user;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-zA-Z]{2,3}$");

        if (pattern.matcher(dto.getUsername()).find()) {
            user = repository.findByEmail(dto.getUsername()).orElseThrow(() ->
                    new BadRequestException("Email")
            );
        } else {
            user = repository.findByLogin(dto.getUsername()).orElseThrow(() ->
                    new BadRequestException("Login")
            );
        }

        var equalsPassword = encoder.matches(dto.getPassword(), user.getPassword());

        if (!equalsPassword) {
            throw new BadRequestException("Password");
        }

        var tokens = tokenService.generateTokens(user);

        return tokens;
    }

    @Override
    @Transactional
    public JwtFullDto refresh(RefreshAccessTokenDto dto) {
        if (tokenService.expiredToken(dto.getRefreshToken())) {
            throw new BadRequestException();
        }

        return tokenService.refresh(dto.getRefreshToken());
    }

    @Override
    public void logout(RefreshAccessTokenDto dto) {
        tokenService.delete(dto.getRefreshToken());
    }

    @Override
    @Transactional
    public UserFullDto update(UserUpdateDto dto) {
        var user = repository.findById(dto.getId()).orElseThrow(() ->
                new NotFoundException(dto.getId().toString())
        );

        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())) {
            repository.findByEmail(dto.getEmail()).ifPresent(item -> {
                throw new BadRequestException("Email");
            });
        }

        if (!user.getLogin().equalsIgnoreCase(dto.getLogin())) {
            repository.findByLogin(dto.getLogin()).ifPresent(item -> {
                throw new BadRequestException("Login");
            });
        }

        mapper.mapToEntity(user, dto);

        var result = repository.save(user);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public UserFullDto delete(Long id) {
        var user = repository.findById(id).orElseThrow(() ->
                new NotFoundException(id.toString())
        );

        repository.delete(user);

        return mapper.mapToDto(user);
    }
}
