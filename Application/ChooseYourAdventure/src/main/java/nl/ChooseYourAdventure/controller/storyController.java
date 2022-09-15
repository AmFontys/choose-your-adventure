package nl.ChooseYourAdventure.controller;


import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.model.Story;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/story")
@AllArgsConstructor
public class storyController {

    private List<Story> stories;

    public storyController(){
        stories = new ArrayList<>();
        stories.add(new Story("Apple#1","Apple Thief"));
        stories.add(new Story("Apple#2","Apple Thief 2"));
        stories.add(new Story("Apple#3","Apple Thief 3"));
        stories.add(new Story("Apple#4","Apple Thief 4"));
    }

    @GetMapping("")
    public  List<Story> list(){
        return  stories;
    }

    @GetMapping("/{id}")
    public Story getStory(@PathVariable String id){
        return  stories.stream().filter(s -> id.equals(s.getId())).findAny().orElse(null);
    }

}
