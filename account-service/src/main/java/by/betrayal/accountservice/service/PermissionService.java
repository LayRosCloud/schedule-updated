package by.betrayal.accountservice.service;

import by.betrayal.accountservice.entity.PermissionEntity;
import by.betrayal.accountservice.entity.Scope;

import java.util.List;

public interface PermissionService {
    List<Scope> findAll();
}
