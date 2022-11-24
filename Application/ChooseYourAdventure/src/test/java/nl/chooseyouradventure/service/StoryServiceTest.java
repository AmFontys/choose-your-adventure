package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.StoryMapper;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

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

    private UserDta userDta;
@Mock
    private StoryMapper storyMapper= new StoryMapper();
    private User user;
    @BeforeEach
    public void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        storyRepository = Mockito.mock(StoryRepository.class);

        storyService = new StoryServiceImpl(storyRepository,userRepository, storyMapper,storybodyRepository,storybodyTypeRepository);

        userDta = UserDta.builder()
                .userid(200)
                .username("Ramen")
                .password("passwordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build();

        user = User.builder()
                .userid(200)
                .username("Ramen")
                .password("passwordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build();

        story = Story.builder()
                .title("NewTitle")
                .user(user)
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

        Story expectstory = Story.builder()
                .title("NewTitle2")
                .user(user)
                .build();

        given(userRepository.findByUsername(userDta.getUsername())).willReturn(Optional.ofNullable(user));
        given(storyRepository.save(expectstory)).willReturn(expectstory);

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
        Story story2 = Story.builder()
                .title("SecondStory")
                .user(user)
                .build();

        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story2);
        given(storyRepository.findById(1)).willReturn(Optional.ofNullable(stories.get(0)));
        // when -  action or the behaviour that we are going test
        Optional<StoryDta> actualStory = storyService.getStory(-1);
        // then - verify the output
        assertThat(actualStory).isEmpty();
    }
}