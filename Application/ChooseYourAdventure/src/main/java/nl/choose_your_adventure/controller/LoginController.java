package nl.choose_your_adventure.controller;

import lombok.AllArgsConstructor;
import nl.choose_your_adventure.Service.loginService;
import nl.choose_your_adventure.model.dta.UserDta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins =  {"http://localhost:3000/"})
@AllArgsConstructor
public class LoginController {

    private loginService loginService;

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
