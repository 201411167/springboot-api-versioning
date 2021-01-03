package me.minjun.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import me.minjun.demo.aop.LogExecutionTime;
import me.minjun.demo.aop.LogExecutionTime;
import me.minjun.demo.domain.user.User;
import me.minjun.demo.domain.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class WebRestController {

    private final UserRepository userRepository;

    @ApiVersion({1,1.1}) // -> localhost:8080/v1.0/api/user/list
    @GetMapping("/user/list")
    public List<User> getAllUsers(HttpServletRequest request){
        List<User> allUsers = userRepository.findAll();
        log.info("creating user list");
        return allUsers;
    }

    @LogExecutionTime // -> aop for time measurement
    @PostMapping("/user/add")
    public void addUser(@RequestBody Map<String, Object> req) {
        log.info("receiving data");
        String email = (String) req.get("email");
        String name = (String) req.get("name");
        userRepository.save(User.builder().name(name).email(email).build());
    }

    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestBody Map<String, Object> req){
        log.info("deleting user");
        userRepository.deleteById((String) req.get("email"));
    }
}
