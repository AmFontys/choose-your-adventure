package nl.choose_your_adventure.controller;


import lombok.AllArgsConstructor;
import nl.choose_your_adventure.Service.userService;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class userController {

    userService service;

    @GetMapping()
    public List<User> getUsers(){
        return service.getUsers();
    }

    @PutMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateUser(@RequestBody UserDta account){

        service.updateUser(account);
        //return "updated";
    }

    @DeleteMapping("")
    public void deleteUser(@RequestBody UserDta account){
        service.deleteUser(account);
    }
}
