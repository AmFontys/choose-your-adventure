package nl.choose_your_adventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.choose_your_adventure.Service.StoryService;
import nl.choose_your_adventure.model.Entity.Story;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.StoryDta;
import nl.choose_your_adventure.persistence.StoryRepository;
import nl.choose_your_adventure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoryServiceImpl implements StoryService {


    private StoryRepository storyRepository;

    private UserRepository userRepository;

    @Override
    public Story saveStory(StoryDta storyDta) {
        if(storyDta.getUser() != null) {
            if(userRepository.findByUsername( storyDta.getUser().getUsername()).isPresent()) {
                User user = User.builder()
                        .userid(storyDta.getUser().getUserid())
                        .username(storyDta.getUser().getUsername())
                        .password(storyDta.getUser().getPassword())
                        .keyword(storyDta.getUser().getKeyword())
                        .email(storyDta.getUser().getEmail())
                        .ismod(storyDta.getUser().getIsmod())
                        .build();

                Story story = Story.builder()
                        .title(storyDta.getTitle())
                        .user(user)
                        .build();

                return storyRepository.save(story);
            }
        }
        return  null;
    }

    @Override
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    //TODO return only the stories of the logged-in user
    @Override
    public List<Story> getAllStories(Integer userId) {
        return storyRepository.findAllById(Collections.singleton(userId));
    }

    @Override
    public Optional<Story> getStory(Integer id) {
        return storyRepository.findById(id);
    }

}
