package nl.chooseyouradventure.controller;


import lombok.AllArgsConstructor;
import nl.chooseyouradventure.configuration.security.isauthenticated.IsAuthenticated;
import nl.chooseyouradventure.service.StoryService;
import nl.chooseyouradventure.service.UserService;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class UserController {

    UserService service;
    private StoryService storyService;

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER","ROLE_MOD"})
    @GetMapping()
    public List<User> getUsers(){
        return service.getUsers();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MOD"})
    @PutMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public void updateUser(@RequestBody UserDta account){

        service.updateUser(account);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MOD"})
    @DeleteMapping("")
    public Optional<String> deleteUser(@RequestBody UserDta account){
        storyService.deleteUsersStory(account);
        return service.deleteUser(account);
    }
}
