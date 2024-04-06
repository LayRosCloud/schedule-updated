package by.betrayal.accountservice.controller;

import by.betrayal.accountservice.entity.Scope;
import by.betrayal.accountservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService service;

    @GetMapping
    public ResponseEntity<List<Scope>> findAll() {
        var list = service.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
