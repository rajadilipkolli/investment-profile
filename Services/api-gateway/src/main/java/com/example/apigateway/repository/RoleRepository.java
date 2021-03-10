package com.example.apigateway.repository;

import com.example.apigateway.models.ERole;
import com.example.apigateway.models.Role;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
