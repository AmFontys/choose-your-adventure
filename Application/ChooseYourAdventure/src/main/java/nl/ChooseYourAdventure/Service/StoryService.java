package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.Entity.Story;

import java.util.List;
import java.util.Optional;

public interface StoryService {
    Story saveStory(Story story);
    List<Story> getAllStories();
    List<Story> getAllStories(Integer userId);
    Optional<Story> getStory(Integer id);

}
