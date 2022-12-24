/* Licensed under Apache-2.0 2022 */
package com.zakura.apigateway.repository;

import com.zakura.apigateway.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveUserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByPan(String pan);

    Mono<Boolean> existsByPhone(long pan);
}
