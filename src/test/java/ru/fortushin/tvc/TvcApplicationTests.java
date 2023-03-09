package ru.fortushin.tvc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.fortushin.tvc.model.User;
import ru.fortushin.tvc.repository.UserRepository;

import java.util.List;

@SpringBootTest
class TvcApplicationTests {
    private final UserRepository userRepository;

    @Autowired
    TvcApplicationTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Test
    void getUsers() {
        List<User> users = userRepository.findAll();
        for(User user : users){
            System.out.println(user);
        }
    }

}
