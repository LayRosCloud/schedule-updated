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
import by.betrayal.accountservice.utils.ThrowableUtils;
import by.betrayal.accountservice.utils.pageable.PageableOptions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final PermissionRepository permissionRepository;
    private final TokenService tokenService;

    private static final String AUTHORIZATION_EXCEPTION = "Username or password is incorrect";

    @Override
    @Transactional
    public List<UserFullDto> findAll(PageableOptions options) {
        var pageable = PageRequest.of(options.page(), options.limit());

        var users = repository.findAll(pageable);

        return mapper.mapToListDto(users.toList());
    }

    @Override
    @Transactional
    public UserFullDto findById(Long id) {
        var user = findByIdOrThrowNotFoundException(id);

        return mapper.mapToDto(user);
    }

    @Override
    @Transactional
    public UserFullDto create(UserCreateDto dto) {
        repository.findByEmailOrLogin(dto.getEmail(), dto.getLogin()).ifPresent(item -> {
                throw ThrowableUtils.getBadRequestException();
            }
        );

        var item = mapper.mapToEntity(dto);

        item.setCreatedAt(System.currentTimeMillis());
        item.setPassword(encoder.encode(dto.getPassword()));
        item.setIsBanned(false);
        item.setIsActivated(false);

        var result = repository.save(item);

        savePermissions(result, dto.getScopes());

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public JwtFullDto login(UserLoginDto dto) {
        UserEntity user = findByUsername(dto.getUsername());

        var equalsPassword = encoder.matches(dto.getPassword(), user.getPassword());

        if (!equalsPassword) {
            throw ThrowableUtils.getBadRequestException(AUTHORIZATION_EXCEPTION);
        }

        var tokens = tokenService.generateTokens(user);

        return tokens;
    }

    @Override
    @Transactional
    public JwtFullDto refresh(RefreshAccessTokenDto dto) {
        if (tokenService.expiredToken(dto.getRefreshToken())) {
            throw ThrowableUtils.getNotAuthorizationException();
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
        var user = findByIdOrThrowNotFoundException(dto.getId());

        notExistsLoginOrThrowBadRequestException(user, dto);

        notExistsMailOrThrowBadRequestException(user, dto);

        mapper.mapToEntity(user, dto);

        var result = repository.save(user);

        return mapper.mapToDto(result);
    }

    @Override
    @Transactional
    public UserFullDto delete(Long id) {
        var user = findByIdOrThrowNotFoundException(id);

        repository.delete(user);

        return mapper.mapToDto(user);
    }

    private UserEntity findByIdOrThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() ->
                ThrowableUtils.getNotFoundException("User with id `%d` is not found", id)
        );
    }

    private void notExistsLoginOrThrowBadRequestException(UserEntity user, UserUpdateDto dto) {
        if (!user.getLogin().equalsIgnoreCase(dto.getLogin())) {
            repository.findByLogin(dto.getLogin()).ifPresent(item -> {
                throw ThrowableUtils.getBadRequestException("Login `%s` exists");
            });
        }
    }

    private void notExistsMailOrThrowBadRequestException(UserEntity user, UserUpdateDto dto) {
        if (!user.getEmail().equalsIgnoreCase(dto.getEmail())) {
            repository.findByEmail(dto.getEmail()).ifPresent(item -> {
                throw ThrowableUtils.getBadRequestException("Email `%s` exists");
            });
        }
    }

    private UserEntity findByUsername(String username) {
        final var patternMail = "^[a-zA-Z0-9._]+@[a-zA-Z]+\\.[a-zA-Z]{2,3}$";
        UserEntity user;
        var pattern = Pattern.compile(patternMail);
        var matcher = pattern.matcher(username);
        if (matcher.find()) {
            user = repository.findByEmail(username).orElseThrow(() ->
                    ThrowableUtils.getBadRequestException(AUTHORIZATION_EXCEPTION)
            );
        } else {
            user = repository.findByLogin(username).orElseThrow(() ->
                    ThrowableUtils.getBadRequestException(AUTHORIZATION_EXCEPTION)
            );
        }

        return user;
    }

    private void savePermissions(UserEntity user, Set<String> scopes) {
        Set<PermissionEntity> scopeSet = new HashSet<>();

        for (String scopeString : scopes) {
            var scope = Scope.valueOf(scopeString);
            var permission = new PermissionEntity();
            permission.setScope(scope);
            permission.setUser(user);
            scopeSet.add(permission);
        }

        permissionRepository.saveAll(scopeSet);
    }
}
