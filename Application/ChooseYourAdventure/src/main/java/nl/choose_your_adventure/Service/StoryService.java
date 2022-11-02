package nl.choose_your_adventure.Service;

import nl.choose_your_adventure.model.Entity.Story;
import nl.choose_your_adventure.model.dta.StoryDta;

import java.util.List;
import java.util.Optional;

public interface StoryService {
    Story saveStory(StoryDta story);
    List<Story> getAllStories();
    List<Story> getAllStories(Integer userId);
    Optional<Story> getStory(Integer id);

}
