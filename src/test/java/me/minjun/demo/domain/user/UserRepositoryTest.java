package me.minjun.demo.domain.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    @After
    public void init(){
        userRepository.deleteAll();
    }

    @Test
    public void 유저추가(){
        userRepository.save(User.builder().email("test_email").name("test_user").build());
    }

    @Test
    public void 유저확인(){
        userRepository.save(User.builder().email("test_email").name("test_user").build());
        User user = userRepository.findById("test_email").get();
        assertEquals(user.getEmail(), "test_email");
        assertEquals(user.getName(), "test_user");

    }
}