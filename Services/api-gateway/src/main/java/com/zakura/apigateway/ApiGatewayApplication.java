package com.zakura.apigateway;

import com.zakura.apigateway.models.ERole;
import com.zakura.apigateway.models.Role;
import com.zakura.apigateway.models.User;
import com.zakura.apigateway.repository.RoleRepository;
import com.zakura.apigateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.ArrayList;
import java.util.List;

@EnableFeignClients
@SpringBootApplication
public class ApiGatewayApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) {
		if (roleRepository.count() == 0){
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
			this.roleRepository.saveAll(roles);
		}
		if (!userRepository.existsByPan("ABCD234567")) {
			User user = new User();
			user.setFirstName("firstName");
			user.setLastName("lastName");
			user.setEmail("test@mail.com");
			user.setPassword("$2a$10$mrq0WtVfOzS0SuDYCjLoVOSwmgUlJE7z4Iq8fC3LQkqNayzrY9tXq");
			user.setPan("ABCD234567");
			user.setPhone(1234323432L);
			this.userRepository.save(user);
		}
	}
}
