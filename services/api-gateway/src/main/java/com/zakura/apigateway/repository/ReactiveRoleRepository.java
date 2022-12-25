/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.repository;

import com.zakura.apigateway.models.ERole;
import com.zakura.apigateway.models.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveRoleRepository extends ReactiveMongoRepository<Role, String> {

    @Cacheable(value = "roles")
    Mono<Role> findByName(ERole name);
}
