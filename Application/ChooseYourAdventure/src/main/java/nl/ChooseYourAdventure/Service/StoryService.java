package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.Story;

import java.util.List;
import java.util.Optional;

public interface StoryService {
    Story saveStory(Story story);
    List<Story> getAllStories();
    Optional<Story> getStory(Integer id);
}
