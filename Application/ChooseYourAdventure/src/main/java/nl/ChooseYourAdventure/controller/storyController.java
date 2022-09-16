package nl.ChooseYourAdventure.controller;


import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.model.Story;
import nl.ChooseYourAdventure.model.StoryBody;
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

        List<StoryBody> list = new ArrayList<>();
        StoryBody body = new StoryBody(1,"apple","Introduction","This a line of text",null);
        list.add(body);

        stories = new ArrayList<>();
        stories.add(new Story("apple","Apple Thief", list ));
        stories.add(new Story("orange","Orange Thief 2",null));
        stories.add(new Story("banana","Banana Thief 3",null));
        stories.add(new Story("pear","Pear Thief 4",null));
    }

    @GetMapping("")
    public  List<Story> list(){
        return  stories;
    }

    @GetMapping("{id}")
    public Story getStory(@PathVariable String id){
        return  stories.stream().filter(s -> id.equals(s.getId())).findAny().orElse(null);
    }

    @GetMapping("body/{storyid}/{id}")
    public StoryBody getStoryBody(@PathVariable String storyid, @PathVariable String id){
        //Later this should first look up the body and then add it to the correct story
        return stories.stream().filter(s -> storyid.equals(s.getId())).findAny().orElse(null)
                .getStoryBodyList().stream().filter(b-> id.equals(b.getId())).findAny().orElse(null);
        }

}
