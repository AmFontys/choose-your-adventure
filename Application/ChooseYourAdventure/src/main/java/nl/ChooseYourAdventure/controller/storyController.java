package nl.ChooseYourAdventure.controller;


import nl.ChooseYourAdventure.Service.StoryService;
import nl.ChooseYourAdventure.model.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/story")
public class storyController {
    @Autowired
    private StoryService storyService;

    @PostMapping("/add")
    public String add(@RequestBody Story story){
        storyService.saveStory(story);
        return "New Story added";
    }

      @GetMapping("")
    public List<Story> list(){
        return  storyService.getAllStories();
    }

    @GetMapping("{id}")
    public Optional<Story> getStory(@PathVariable Integer id){
        return  storyService.getStory(id);
    }
/*
   @GetMapping("body/{storyid}/{id}")
    public StoryBody getStoryBody(@PathVariable String storyid, @PathVariable String id){
        //Later this should first look up the body and then add it to the correct story
       return stories.stream().filter(s -> storyid.equals(s.getId())).findAny().orElse(null)
                .getStoryBodyList().stream().filter(b-> id.equals(b.getId())).findAny().orElse(null);
        }*/


}
