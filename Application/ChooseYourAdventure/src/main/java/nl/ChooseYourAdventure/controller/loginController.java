package nl.ChooseYourAdventure.controller;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.Service.StoryService;
import nl.ChooseYourAdventure.Service.loginService;
import nl.ChooseYourAdventure.Service.userService;
import nl.ChooseYourAdventure.model.User;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins =  {"http://localhost:3000/"})
@AllArgsConstructor
public class LoginController {

    private userService userService;

    private loginService loginService;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody User account){
        return loginService.saveUser(account);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/login")
    public User getLoginPage(@RequestParam("username") String username,@RequestParam("password") String pass){
        User user = loginService.loginUser(username,pass);
        return user;
    }

}
