package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.StoryMapper;
import nl.chooseyouradventure.model.Usermapper;
import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.dta.StorybodyDta;
import nl.chooseyouradventure.model.entity.StoryBodyType;
import nl.chooseyouradventure.model.entity.Storybody;
import nl.chooseyouradventure.persistence.StorybodyRepository;
import nl.chooseyouradventure.persistence.StorybodyTypeRepository;
import nl.chooseyouradventure.service.impl.StoryServiceImpl;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.persistence.StoryRepository;
import nl.chooseyouradventure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoryServiceTest {

    @Mock
    private StoryRepository storyRepository;
    @Mock
    private StorybodyRepository storybodyRepository;
    @Mock
    private StorybodyTypeRepository storybodyTypeRepository;

    @Mock
    UserRepository userRepository;
    @InjectMocks
    private StoryServiceImpl storyService;

    private Story story;

    private Storybody storybody;

    private UserDta userDta;

    private final StoryMapper storyMapper= new StoryMapper();
    private User user;
    @BeforeEach
    public void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        storyRepository = Mockito.mock(StoryRepository.class);
        storybodyRepository = Mockito.mock(StorybodyRepository.class);
        storybodyTypeRepository = Mockito.mock(StorybodyTypeRepository.class);

        storyService = new StoryServiceImpl(storyRepository,userRepository, storyMapper,storybodyRepository,storybodyTypeRepository);

        userDta = UserDta.builder()
                .userid(200)
                .username("Ramen")
                .password("passwordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build();

        user = Usermapper.giveEntity(userDta);

        story = Story.builder()
                .title("NewTitle")
                .user(user)
                .build();

        storybody = Storybody.builder()
                .bodyTitle("title of amazing")
                .type(StoryBodyType.builder().storyBodyTypeId(1).typename("Intro").build())
                .story(story)
                .text("Introduction text")
                .build();
    }
    @Test
    void saveStory_noUser() {
        // given - precondition or setup
        StoryDta storyDta = StoryDta.builder()
                .title("NewStory")
                .user(null)
                .build();

        // when -  action or the behaviour that we are going test
        Story actualStory = storyService.saveStory(storyDta);
        // then - verify the output
        assertThat(actualStory).isNull();
    }

    @Test
    void saveStory_withUser() {
        // given - precondition or setup
        StoryDta storyDta = StoryDta.builder()
                .title("NewTitle2")
                .user(userDta)
                .build();

        Story expectedStory = Story.builder()
                .title("NewTitle2")
                .user(user)
                .build();

        given(userRepository.findByUsername(userDta.getUsername())).willReturn(Optional.ofNullable(user));
        given(storyRepository.save(expectedStory)).willReturn(expectedStory);

        // when -  action or the behaviour that we are going test
        Story actualStory = storyService.saveStory(storyDta);
        // then - verify the output
        assertThat(actualStory).isNotNull();
    }

    @Test
    void getAllStories() {
        // given - precondition or setup
        Story story2 = Story.builder()
                .title("SecondStory")
                .user(user)
                .build();

        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story2);
        given(storyRepository.findAll()).willReturn(stories);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories();
        // then - verify the output
        assertThat(actualStories).isNotNull();
        assertThat(actualStories).hasSize(2);
    }

    @Test
    void GetAllStoriesUser() {
        // given - precondition or setup
        Story story2 = Story.builder()
                .title("SecondStory")
                .user(user)
                .build();

        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story2);
        given(storyRepository.findAllByUserUserid(1)).willReturn(stories);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories(1);
        // then - verify the output
        assertThat(actualStories).isNotNull();
        assertThat(actualStories).hasSize(2);
    }

    @Test
    void GetAllStoriesUser_withInValidId() {
        // given - precondition or setup

        List<Story> stories = new ArrayList<>();
        given(storyRepository.findAllByUserUserid(0)).willReturn(stories);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories(0);
        // then - verify the output
        assertThat(actualStories).isNullOrEmpty();
    }
    @Test
    void getStory_withValidId() {
        Story story2 = Story.builder()
                .title("SecondStory")
                .user(user)
                .build();

        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story2);
        given(storyRepository.findById(1)).willReturn(Optional.ofNullable(stories.get(0)));
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStory = storyService.getStory(1);
        // then - verify the output
        assertThat(actualStory).isNotNull();
        assertThat(actualStory).isPresent();
        assertThat(actualStory.get().getTitle()).isEqualTo("NewTitle");
    }
    @Test
    void getStory_withInValidId() {

        given(storyRepository.findById(-1)).willReturn(Optional.empty());
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStory = storyService.getStory(-1);
        // then - verify the output
        assertThat(actualStory).isEmpty();
    }


    @Test
    void deleteUserStory_NoValidId(){
// given - precondition or setup

       user.setUserid(0);

        // when -  action or the behaviour that we are going test
        storyService.deleteUsersStory(Usermapper.giveDta(user));
        // then - verify the output


        verify(storyRepository,never()).findAllByUser(user);
        verify(storyRepository,never()).deleteAll(any());
    }

    @Test
    void deleteUserStory_ValidId(){
// given - precondition or setup


        // when -  action or the behaviour that we are going test
        storyService.deleteUsersStory(Usermapper.giveDta(user));
        // then - verify the output

        verify(storyRepository).findAllByUser(any());
        verify(storyRepository).deleteAll(any());
    }
    @Test
    void deleteStory_NoValidId(){
// given - precondition or setup

        user.setUserid(0);
        // when -  action or the behaviour that we are going test
        storyService.deleteUsersStory(Usermapper.giveDta(user));
        // then - verify the output
        verify(storyRepository,never()).deleteByUser(user);
    }

    @Test
    void deleteStory_ValidId(){
// given - precondition or setup


        // when -  action or the behaviour that we are going test
        storyService.deleteStory(1);
        // then - verify the output
        verify(storyRepository).deleteById(1);
    }

    @Test
    void getStoryBody_validId(){
        // given - precondition or setup

        List<Storybody> stories = new ArrayList<>();
        stories.add(storybody);
        stories.add(storybody);
        given(storybodyRepository.findAllByStoryStoryid(1)).willReturn(stories);
        // when -  action or the behaviour that we are going test
        List<StorybodyDta> actualStories = storyService.getStoryBody(1);
        // then - verify the output
        assertThat(actualStories).isNotNull();
        assertThat(actualStories).hasSize(2);
    }

    @Test
    void getStoryBody_InvalidId(){
// given - precondition or setup


        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories(0);
        // then - verify the output
        assertThat(actualStories).isNull();
    }

    @Test
    void saveStoryBody_Success(){
// given - precondition or setup
StoryBodyType type = StoryBodyType.builder().storyBodyTypeId(1).typename("Intro").build();



given(storybodyTypeRepository.findByTypename("Intro")).willReturn(type);
given(storybodyRepository.save(storybody)).willReturn(storybody);
        // when -  action or the behaviour that we are going test
        StorybodyDta actualStories = storyService.saveStoryBody(Optional.of(storyMapper.giveDtaStory( story)), storyMapper.giveDtaStorybody(storybody));
        // then - verify the output
        assertThat(actualStories).isNotNull();
    }

    @Test
    void saveStoryBody_InvalidStoryIsNull(){
// given - precondition or setup

        storybody.setStory(null);
        // when -  action or the behaviour that we are going test
        StorybodyDta actualStories = storyService.saveStoryBody(null, storyMapper.giveDtaStorybody(storybody));
        // then - verify the output
        assertThat(actualStories).isNull();
    }


    @Test
    void saveStoryBody_InvalidStoryTypeIsNull(){
// given - precondition or setup

        storybody.setType(null);


        // when -  action or the behaviour that we are going test
        StorybodyDta actualStories = storyService.saveStoryBody(Optional.of(storyMapper.giveDtaStory( story)),
                storyMapper.giveDtaStorybody(storybody));
        // then - verify the output
        assertThat(actualStories).isNull();
    }
    @Test
    void saveStoryBody_InvalidStoryBodyIsNull(){
// given - precondition or setup


        // when -  action or the behaviour that we are going test
        StorybodyDta actualStories = storyService.saveStoryBody(Optional.of(storyMapper.giveDtaStory( story)), null);
        // then - verify the output
        assertThat(actualStories).isNull();
    }


    @Test
    void getStoryBodyType(){
// given - precondition or setup



        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualStories = storyService.getStoryBodyType();
        // then - verify the output
        assertThat(actualStories).isNotNull();
    }
    @Test
    void getStoryBodyType_NoValidNameIsNull(){
// given - precondition or setup



        // when -  action or the behaviour that we are going test
        List<StoryBodyTypeDta> actualStories = storyService.getStoryBodyType();
        // then - verify the output
        assertThat(actualStories).isNotNull();
    }
    @Test
    void getStoryBodyType_NoValidNameIsEmpty(){
// given - precondition or setup

//needs to be empty
String name="";
        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta actualBodyType = storyService.getStoryBodyType(name);
        // then - verify the output
        assertThat(actualBodyType).isNull();
    }

    @Test
    void getStoryBodyType_NoValidNameIsEmptyString(){
// given - precondition or setup



        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta actualBodyType = storyService.getStoryBodyType("");
        // then - verify the output
        assertThat(actualBodyType).isNull();
    }
    @Test
    void getStoryBodyType_ValidName(){
// given - precondition or setup
StoryBodyType bodyType =StoryBodyType.builder().storyBodyTypeId(1).typename("Intro").build();


        given(storybodyTypeRepository.findByTypename("Index")).willReturn(bodyType);
        // when -  action or the behaviour that we are going test
        StoryBodyTypeDta type = storyService.getStoryBodyType("Index");
        // then - verify the output
        assertThat(type).isNotNull();
    }
    @Test
    void getAllStoriesBasedOnName_NameIsNull(){
// given - precondition or setup


String name = null;
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories(name);
        // then - verify the output
        assertThat(actualStories).isNull();
    }
    @Test
    void getAllStoriesBasedOnName_NameIsEmptyString(){
// given - precondition or setup



        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories("");
        // then - verify the output
        assertThat(actualStories).isNull();
    }
    @Test
    void getAllStoriesBasedOnName_SizeIsNull(){
// given - precondition or setup

List<Story> emptyList = new ArrayList<>();
given(storyRepository.findAllByTitleContaining("size")).willReturn(emptyList);
        // when -  action or the behaviour that we are going test
        List<StoryDta> actualStories = storyService.getAllStories("size");
        // then - verify the output
        assertThat(actualStories).isNull();
    }

}