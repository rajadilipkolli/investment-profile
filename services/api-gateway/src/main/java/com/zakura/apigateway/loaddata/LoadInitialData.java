/* Licensed under Apache-2.0 2021-2022 */
package com.zakura.apigateway.loaddata;

import com.zakura.apigateway.models.ERole;
import com.zakura.apigateway.models.Role;
import com.zakura.apigateway.models.User;
import com.zakura.apigateway.repository.ReactiveRoleRepository;
import com.zakura.apigateway.repository.ReactiveUserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoadInitialData implements CommandLineRunner {

    private final ReactiveRoleRepository reactiveRoleRepository;

    private final ReactiveUserRepository reactiveUserRepository;

    @Override
    public void run(String... args) {
        List<Role> roles = new ArrayList<>();
        Role roleUser = new Role();
        roleUser.setName(ERole.ROLE_USER);
        roles.add(roleUser);
        Role moderatorUser = new Role();
        moderatorUser.setName(ERole.ROLE_MODERATOR);
        roles.add(moderatorUser);
        Role adminUser = new Role();
        adminUser.setName(ERole.ROLE_ADMIN);
        roles.add(adminUser);

        this.reactiveRoleRepository
                .deleteAll()
                .thenMany(Flux.fromIterable(roles).flatMap(this.reactiveRoleRepository::save).log())
                .then(this.reactiveUserRepository.deleteAll())
                .then(getUserMono().flatMap(this.reactiveUserRepository::save))
                .subscribe(
                        data -> log.info("data: {}", data),
                        err -> log.error("error:", err),
                        () -> log.info("data initialization done..."));
    }

    private Mono<User> getUserMono() {
        Set<Role> roleSet = new HashSet<>();
        return this.reactiveRoleRepository
                .findByName(ERole.ROLE_USER)
                .map(roleSet::add)
                .flatMap(
                        aBoolean -> {
                            User user = new User();
                            user.setFirstName("firstName");
                            user.setLastName("lastName");
                            user.setEmail("test@mail.com");
                            user.setPassword(
                                    "$2a$10$mrq0WtVfOzS0SuDYCjLoVOSwmgUlJE7z4Iq8fC3LQkqNayzrY9tXq");
                            user.setPan("ABCD234567");
                            user.setPhone(1234323432L);
                            user.setRoles(roleSet);
                            return Mono.just(user);
                        });
    }
}
