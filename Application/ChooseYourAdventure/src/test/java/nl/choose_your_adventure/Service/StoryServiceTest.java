package nl.choose_your_adventure.Service;

import nl.choose_your_adventure.Service.impl.StoryServiceImpl;
import nl.choose_your_adventure.model.Entity.Story;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.StoryDta;
import nl.choose_your_adventure.model.dta.UserDta;
import nl.choose_your_adventure.persistence.StoryRepository;
import nl.choose_your_adventure.persistence.UserRepository;
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
    UserRepository userRepository;
    @InjectMocks
    private StoryServiceImpl storyService;

    private Story story;

    private UserDta userDta;

    private User user;
    @BeforeEach
    public void setup(){
        userRepository = Mockito.mock(UserRepository.class);
        storyRepository = Mockito.mock(StoryRepository.class);
        storyService = new StoryServiceImpl(storyRepository,userRepository);

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
        List<Story> actualStories = storyService.getAllStories();
        // then - verify the output
        assertThat(actualStories).isNotNull();
        assertThat(actualStories.size()).isEqualTo(2);
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
        Optional<Story> actualStory = storyService.getStory(1);
        // then - verify the output
        assertThat(actualStory).isNotNull();
        assertThat(actualStory.isPresent()).isTrue();
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
        Optional<Story> actualStory = storyService.getStory(-1);
        // then - verify the output
        assertThat(actualStory).isEmpty();
    }
}