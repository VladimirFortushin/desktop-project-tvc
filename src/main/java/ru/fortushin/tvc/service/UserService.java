package ru.fortushin.tvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fortushin.tvc.javaFx.RegistrationPageController;
import ru.fortushin.tvc.repository.UserRepository;
import ru.fortushin.tvc.securityDetails.UserDetails;
import ru.fortushin.tvc.util.EmployeeNotFoundException;
import ru.fortushin.tvc.model.User;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public void createNewUser(User user){
        userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByLogin(login);
        if (user.isEmpty()) {
            throw new EmployeeNotFoundException("User not found");
        }
        return new UserDetails(user.get());
    }
}
