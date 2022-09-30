package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.Story;
import nl.ChooseYourAdventure.persistence.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public Story saveStory(Story story) {
        return storyRepository.save(story);
    }

    @Override
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    @Override
    public Optional<Story> getStory(Integer id) {
        return storyRepository.findById(id);
    }
}
