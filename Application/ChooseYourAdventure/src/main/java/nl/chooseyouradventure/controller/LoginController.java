package nl.chooseyouradventure.controller;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.service.LoginService;
import nl.chooseyouradventure.model.dta.UserDta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins =  {"http://localhost:3000/"})
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody UserDta account){
        loginService.saveUser(account);
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @GetMapping("/login")
    public UserDta getLoginPage(@RequestParam("username") String username, @RequestParam("password") String pass,HttpServletResponse response){

        return loginService.loginUser(username,pass);
    }

}
