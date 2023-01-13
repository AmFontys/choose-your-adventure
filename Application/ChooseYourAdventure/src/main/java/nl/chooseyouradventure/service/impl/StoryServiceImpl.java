package nl.chooseyouradventure.service.impl;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.StoryMapper;
import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.StoryBodyType;
import nl.chooseyouradventure.model.entity.Storybody;
import nl.chooseyouradventure.persistence.StorybodyRepository;
import nl.chooseyouradventure.persistence.StorybodyTypeRepository;
import nl.chooseyouradventure.service.StoryService;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.persistence.StoryRepository;
import nl.chooseyouradventure.persistence.UserRepository;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<StoryDta> allStories= storyMapper.giveDtaStory( storyRepository.findAll());
        if(allStories.size()>0)
            return makeStoryUserSecure(allStories);
        else return null;
    }


    @Override
    public List<StoryDta> getAllStories(Integer userId) {
        List<StoryDta> allStories= storyMapper.giveDtaStory(storyRepository.findAllByUserUserid(userId));
        if(allStories.size()>0)
            return makeStoryUserSecure(allStories);
        else return null;
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
            List<StorybodyDta> dtaList = storyMapper.giveDtaStorybody(storybodyRepository.findAllByStoryStoryid(storyid));
            if(dtaList.size()>0)
                return makeStorybodyUserSecure(dtaList);
        }
        return null;
    }



    @Override
    public StorybodyDta saveStoryBody(Optional<StoryDta> story, StorybodyDta storybodyDta) {
        if(storybodyDta==null || story.isEmpty()) return  null;

        if(storybodyDta.getType()!=null)
            storybodyDta.setType(getStoryBodyType(storybodyDta.getType().getTypename()));

        if(story.isPresent() && (storybodyDta.getType() !=null)) {
            storybodyDta.setStory(story.get());
Storybody storybody = StoryMapper.giveEntityStorybody(storybodyDta);
            return storyMapper.giveDtaStorybody(storybodyRepository.save(storybody));

        }
       return  null;
    }

    @Override
    public List<StoryBodyTypeDta> getStoryBodyType() {
        return storyMapper.giveDtaStorybodyType(storybodyTypeRepository.findAll());
    }

    @Override
    public StoryBodyTypeDta getStoryBodyType(String typename) {
        if(typename.equals("") || typename==null)
            return  null;
        else return storyMapper.giveDtaStorybodyType(storybodyTypeRepository.findByTypename(typename));
    }

    @Override
    public List<StoryDta> getAllStories(String name) {
        if(name==null || name.equals("")) return null;

        List<StoryDta> allSearchedStories= storyMapper.giveDtaStory(storyRepository.findAllByTitleContaining(name));
        if(allSearchedStories.size()>0){
            return makeStoryUserSecure(allSearchedStories);
        }
        return  null;

    }

    @Override
    public List<StoryDta> getAllStories(UserDta user) {
        if(user == null|| user.getUsername() == null ||  user.getUsername().equals("")) return null;

        List<StoryDta> allStoriesByUser = storyMapper.giveDtaStory(storyRepository.findAllByUserUsernameContaining(user.getUsername()));
        if(allStoriesByUser.size()>0)
            return  allStoriesByUser;

        return null;
    }

    @Override
    public String incrementStoryOption(Integer optionId) {
        try {
            Optional<Storybody> optional = storybodyRepository.findById(optionId);
            if(optional.isPresent()) {
                Integer lastChosenNmb = optional.get().getChosen();
                Integer newNumber = lastChosenNmb == null ? 1 : lastChosenNmb + 1;

                storybodyRepository.addOneToChosen(newNumber, optionId);
                return "success";
            }
            return "no success";
        }
        catch (Exception ex){
            return "no success";
        }

    }

    private List<StoryDta> makeStoryUserSecure(List<StoryDta> list){
        for (StoryDta story : list){
            story.getUser().setPassword("secret");
            story.getUser().setKeyword("secret");
            story.getUser().setIsmod(null);

        }
        return list;
    }

    private List<StorybodyDta> makeStorybodyUserSecure(List<StorybodyDta> dtaList) {
        for (StorybodyDta storybodyDta: dtaList){
            storybodyDta.getStory().getUser().setPassword("secret");
            storybodyDta.getStory().getUser().setKeyword("secret");
        }
        return dtaList;
    }
}
