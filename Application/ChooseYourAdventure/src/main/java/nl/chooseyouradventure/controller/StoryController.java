package nl.chooseyouradventure.controller;


import nl.chooseyouradventure.configuration.security.isauthenticated.IsAuthenticated;
import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.entity.StoryBodyType;
import nl.chooseyouradventure.model.entity.Storybody;
import nl.chooseyouradventure.service.StoryService;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.dta.StoryDta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/story")
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class StoryController {
    @Autowired
    private StoryService storyService;

    @PostMapping("")
    public String add(@RequestBody StoryDta story){

        storyService.saveStory(story);
        return "New Story added";
    }

    @GetMapping("")
    public List<StoryDta> list(){
        return  storyService.getAllStories();
    }
    @GetMapping("/search")
    public List<StoryDta> list(@RequestParam("title") String name){
        return  storyService.getAllStories(name);
    }
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MOD"})
    @GetMapping("/user")
    public List<StoryDta> list(@RequestParam("userid") Integer id){
        return  storyService.getAllStories(id);
    }

    @GetMapping("{id}")
    public Optional<StoryDta> getStory(@PathVariable Integer id){
        return  storyService.getStory(id);
    }

    @GetMapping("/body/{storyid}")
    public List<StorybodyDta> getStoryBody(@PathVariable Integer storyid) {
        //Later this should first look up the body and then add it to the correct story
        return storyService.getStoryBody(storyid);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MOD"})
    @PostMapping("/body/{storyid}")
    public String saveStoryBody(@PathVariable Integer storyid,@RequestBody StorybodyDta storybodyDta) {
        //Later this should first look up the body and then add it to the correct story
        Optional<StoryDta> chosenStory = storyService.getStory(storyid);
        StorybodyDta storybody= storyService.saveStoryBody(chosenStory,storybodyDta);
        if(storybody !=null)
            return "New Storybody added";
        else return "Fail!";
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_USER", "ROLE_MOD"})
    @GetMapping("/body/type")
    public List<StoryBodyTypeDta> getStoryBody() {
        return storyService.getStoryBodyType();
    }

    @DeleteMapping("{id}")
    public void deleteStory(@PathVariable Integer id){
        storyService.deleteStory(id);
    }
}
