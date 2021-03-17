package com.example.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.apigateway.models.ERole;
import com.example.apigateway.models.Role;
import com.example.apigateway.repository.RoleRepository;

@EnableFeignClients
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
			Role roleUser = new Role(ERole.ROLE_USER);
			this.roleRepository.save(roleUser);
		}

	}
}
