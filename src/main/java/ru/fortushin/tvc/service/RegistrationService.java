package ru.fortushin.tvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.tvc.model.User;
import ru.fortushin.tvc.repository.UserRepository;
import ru.fortushin.tvc.util.UserNotCreatedException;

@Component
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        user.setLogin(user.getLogin());
        user.setName(user.getName());
        user.setJobTitle(user.getJobTitle());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        user.setEmail(user.getEmail());
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()){
            throw new UserNotCreatedException("Current user already exists!");
        }else{userRepository.save(user);}

    }
}
