package by.betrayal.accountservice.controller;

import by.betrayal.accountservice.dto.token.JwtFullDto;
import by.betrayal.accountservice.dto.token.RefreshAccessTokenDto;
import by.betrayal.accountservice.dto.user.UserCreateDto;
import by.betrayal.accountservice.dto.user.UserFullDto;
import by.betrayal.accountservice.dto.user.UserLoginDto;
import by.betrayal.accountservice.dto.user.UserUpdateDto;
import by.betrayal.accountservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserFullDto>> findAll() {
        var users = service.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserFullDto> findById(@PathVariable Long id) {
        var user = service.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtFullDto> login(@Valid @RequestBody UserLoginDto dto) {
        var user = service.login(dto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtFullDto> refresh(@Valid @RequestBody RefreshAccessTokenDto dto) {
        var token = service.refresh(dto);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshAccessTokenDto dto) {
        service.logout(dto);

        return new ResponseEntity<>("You success exit from system", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserFullDto> create(@Valid @RequestBody UserCreateDto dto) {
        var user = service.create(dto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserFullDto> update(@Valid @RequestBody UserUpdateDto dto) {
        var user = service.update(dto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserFullDto> delete(@PathVariable Long id) {
        var user = service.delete(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}