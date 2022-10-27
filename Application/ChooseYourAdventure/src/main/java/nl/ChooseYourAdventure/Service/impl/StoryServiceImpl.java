package nl.ChooseYourAdventure.Service.impl;

import nl.ChooseYourAdventure.Service.StoryService;
import nl.ChooseYourAdventure.model.Story;
import nl.ChooseYourAdventure.persistence.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

    //TODO return only the stories of the logged in user
    @Override
    public List<Story> getAllStories(Integer userId) {
        return storyRepository.findAllById(Collections.singleton(userId));
    }

    @Override
    public Optional<Story> getStory(Integer id) {
        return storyRepository.findById(id);
    }

}
