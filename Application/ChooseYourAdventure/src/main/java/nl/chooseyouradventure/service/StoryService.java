package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.dta.StoryDta;

import java.util.List;
import java.util.Optional;

public interface StoryService {
    Story saveStory(StoryDta story);
    List<StoryDta> getAllStories();
    List<StoryDta> getAllStories(Integer userId);
    Optional<StoryDta> getStory(Integer id);
    void deleteUsersStory(UserDta account);
    void deleteStory(Integer id);

    List<StorybodyDta> getStoryBody(Integer storyid);

    StorybodyDta saveStoryBody(Optional<StoryDta> storyid, StorybodyDta storybodyDta);

    List<StoryBodyTypeDta> getStoryBodyType();

    StoryBodyTypeDta getStoryBodyType(String typename);

    List<StoryDta> getAllStories(String name);

    List<StoryDta> getAllStories(UserDta user);

    String incrementStoryOption(Integer optionId);
}
