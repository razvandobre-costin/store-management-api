package com.razvandobre.store;

import com.razvandobre.store.model.Role;
import com.razvandobre.store.model.User;
import com.razvandobre.store.repository.RoleRepository;
import com.razvandobre.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Role userRole = roleRepository.findByName("USER");
        if (userRole == null) {
            userRole = roleRepository.save(new Role("USER"));
        }

        Role adminRole = roleRepository.findByName("ADMIN");
        if (adminRole == null) {
            adminRole = roleRepository.save(new Role("ADMIN"));
        }

        if (!userRepository.findByUsername("admin").isPresent()) {
            Set<Role> roles = Set.of(userRole, adminRole);

            User adminUser = new User("admin", passwordEncoder.encode("password"), roles);
            userRepository.save(adminUser);
        }
    }
}
