package by.betrayal.accountservice.service.impl;

import by.betrayal.accountservice.entity.Scope;
import by.betrayal.accountservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    @Override
    public List<Scope> findAll() {
        return List.of(Scope.values());
    }
}
