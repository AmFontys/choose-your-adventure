package nl.ChooseYourAdventure.controller;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.Service.loginService;
import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins =  {"http://localhost:3000/"})
@AllArgsConstructor
public class LoginController {

    private loginService loginService;


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserDta account){
        loginService.saveUser(account);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/login")
    public UserDta getLoginPage(@RequestParam("username") String username, @RequestParam("password") String pass){
        return loginService.loginUser(username,pass);
    }

}
