package nl.choose_your_adventure.controller;


import nl.choose_your_adventure.Service.StoryService;
import nl.choose_your_adventure.model.Entity.Story;
import nl.choose_your_adventure.model.dta.StoryDta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/story")
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class storyController {
    @Autowired
    private StoryService storyService;

    @PostMapping("")
    public String add(@RequestBody StoryDta story){

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
    public Storybody getStoryBody(@PathVariable String storyid, @PathVariable String id){
        //Later this should first look up the body and then add it to the correct story
       return stories.stream().filter(s -> storyid.equals(s.getId())).findAny().orElse(null)
                .getStoryBodyList().stream().filter(b-> id.equals(b.getId())).findAny().orElse(null);
        }*/


}
