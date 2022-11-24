package nl.chooseyouradventure.service.impl;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.StoryMapper;
import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.persistence.StorybodyRepository;
import nl.chooseyouradventure.persistence.StorybodyTypeRepository;
import nl.chooseyouradventure.service.StoryService;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.persistence.StoryRepository;
import nl.chooseyouradventure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoryServiceImpl implements StoryService {


    private StoryRepository storyRepository;

    private UserRepository userRepository;

    private StoryMapper storyMapper;
    private StorybodyRepository storybodyRepository;
    private StorybodyTypeRepository storybodyTypeRepository;

    @Override
    public Story saveStory(StoryDta storyDta) {
        if(storyDta.getUser() != null && userRepository.findByUsername( storyDta.getUser().getUsername()).isPresent()) {
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
        return  null;
    }

    @Override
    public List<StoryDta> getAllStories() {
        return storyMapper.giveDtaStory( storyRepository.findAll());
    }


    @Override
    public List<StoryDta> getAllStories(Integer userId) {
        return storyMapper.giveDtaStory(storyRepository.findAllByUserUserid(userId));
    }

    @Override
    public Optional<StoryDta> getStory(Integer id) {
        return storyMapper.giveDtaStory(storyRepository.findById(id));
    }

    @Override
    public void deleteUsersStory(UserDta account) {
        if (account.getUserid() > 0) {
            User deleteAccount = User.builder()
                    .userid(account.getUserid())
                    .build();
            storyRepository.deleteAll(storyRepository.findAllByUser(deleteAccount));
        }
    }

    @Override
    public void deleteStory(Integer id) {
if(id>0) {
    storyRepository.deleteById(id);
}
    }

    @Override
    public List<StorybodyDta> getStoryBody(Integer storyid) {
        if(storyid>0) {
            return storybodyRepository.findAllByStoryStoryid(storyid);
        }
        return null;
    }

    @Override
    public StorybodyDta saveStoryBody(Optional<StoryDta> story, StorybodyDta storybodyDta) {
        if(storybodyDta==null || story.isEmpty()) return  null;

        if(storybodyDta.getType()!=null)
            storybodyDta.setType(this.getStoryBodyType(storybodyDta.getType().getTypename()));

        if(story.isPresent() && (storybodyDta.getType() !=null)) {
            storybodyDta.setStory(story.get());
            if (story != null) {
                return storyMapper.giveDtaStorybody(storybodyRepository.save(storyMapper.giveEntityStorybody(storybodyDta)));
            }
        }
       return  null;
    }

    @Override
    public List<StoryBodyTypeDta> getStoryBodyType() {
        return storyMapper.giveDtaStorybodyType(storybodyTypeRepository.findAll());
    }

    @Override
    public StoryBodyTypeDta getStoryBodyType(String typename) {
        if(typename.isEmpty() || typename =="" || typename==null)
            return  null;
        else return storyMapper.giveDtaStorybodyType(storybodyTypeRepository.findByTypename(typename));
    }

}
