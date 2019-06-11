package com.dcorn.api.utils.initializer;

import com.dcorn.api.role.Role;
import com.dcorn.api.role.RoleType;
import com.dcorn.api.role.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitialize implements CommandLineRunner {

    private final IRoleRepository ROLE_REPOSITORY;

    @Autowired
    public RoleInitialize(IRoleRepository role_repository) {
        this.ROLE_REPOSITORY = role_repository;
    }

    @Override
    public void run(String... args) {
        for (RoleType role: RoleType.values()) {
            if (!ROLE_REPOSITORY.findRoleByType(role).isPresent()) {
                Role initRole = new Role();
                initRole.setType(role);
                ROLE_REPOSITORY.save(initRole);
                System.out.println(initRole);
            }
        }
    }
}
