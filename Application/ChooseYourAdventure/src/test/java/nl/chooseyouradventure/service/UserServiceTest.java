package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.Usermapper;
import nl.chooseyouradventure.service.impl.UserServiceImp;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImp userService;

    private UserDta userDta;

    private User user;



    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImp(userRepository);

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
    }

    @Test
    void getUsers() {
        // given - precondition or setup
        User user2 = User.builder()
                .userid(200)
                .username("Spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("Spaghetti@gmail.com")
                .ismod(false)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        given(userRepository.findAll()).willReturn(users);
// when -  action or the behaviour that we are going test
        List<User> actualUsers = userService.getUsers();
        // then - verify the output
        assertThat(actualUsers).isNotNull();
        assertThat(actualUsers).hasSize(2);
    }

    @Test
    void updateUser_withValidInput() {
        // given - precondition or setup
        UserDta givenAccount = UserDta.builder()
                .userid(200)
                .username("spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghetti@gmail.com")
                .ismod(true)
                .build();

        Optional<User> optionalUser = Optional.of(User.builder()
                .userid(200)
                .username("spaghet")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghet@gmail.com")
                .ismod(true)
                .build());

        given(userRepository.findById(givenAccount.getUserid())).willReturn(optionalUser);
        given(userRepository.save(Usermapper.giveEntity(givenAccount))).willReturn(Usermapper.giveEntity(givenAccount));
// when -  action or the behaviour that we are going test
 User actualAccount =  userService.updateUser(givenAccount);
        // then - verify the output
        assertThat(actualAccount).isEqualTo(Usermapper.giveEntity(givenAccount));
        verify(userRepository).findById(200);
        verify(userRepository).save(Usermapper.giveEntity(givenAccount));

    }
    @Test
    void updateUser_withEmptyOptional() {
        // given - precondition or setup
        UserDta givenAccount = UserDta.builder()
                .userid(200)
                .username("spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghetti@gmail.com")
                .ismod(true)
                .build();



        given(userRepository.findById(givenAccount.getUserid())).willReturn(Optional.empty());
// when -  action or the behaviour that we are going test
        User actualAccount =  userService.updateUser(givenAccount);
        // then - verify the output
        assertThat(actualAccount).isNull();
        verify(userRepository).findById(200);
        verify(userRepository,never()).save(any());
    }

    @Test
    void updateUser_withInValidId() {
        // given - precondition or setup
        UserDta givenAccount = UserDta.builder()
                .userid(-1)
                .username("spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghetti@gmail.com")
                .ismod(true)
                .build();



        given(userRepository.findById(givenAccount.getUserid())).willReturn(Optional.empty());
// when -  action or the behaviour that we are going test
        User actualAccount =  userService.updateUser(givenAccount);
        // then - verify the output
        assertThat(actualAccount).isNull();
        verify(userRepository).findById(-1);
        verify(userRepository,never()).save(any());
    }

    @Test
    void deleteUser_withValidId() {
        // given - precondition or setup
        UserDta givenAccount = UserDta.builder()
                .userid(200)
                .username("spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghetti@gmail.com")
                .ismod(true)
                .build();

// when -  action or the behaviour that we are going test
        Optional<String> actualResponse =  userService.deleteUser(givenAccount);
        // then - verify the output
        assertThat(actualResponse).isEqualTo(Optional.of("Succesfull"));
        verify(userRepository).deleteById(200);
    }

    @Test
    void deleteUser_withInvalidId() {
        // given - precondition or setup
        UserDta givenAccount = UserDta.builder()
                .userid(-1)
                .username("spaghetti")
                .password("passwordEncoded")
                .keyword("key")
                .email("spaghetti@gmail.com")
                .ismod(true)
                .build();

// when -  action or the behaviour that we are going test
        Optional<String> actualResponse =  userService.deleteUser(givenAccount);
        // then - verify the output
        assertThat(actualResponse).isEqualTo(Optional.of("This id doesn't exist"));
        verify(userRepository,never()).deleteById(any());
    }

    @Test
    void deleteUser_withINullEntered() {
        // given - precondition or setup

// when -  action or the behaviour that we are going test
        Optional<String> actualResponse =  userService.deleteUser(null);
        // then - verify the output
        assertThat(actualResponse).isEqualTo(Optional.of("Cannot invoke \"nl.chooseyouradventure.model.dta.UserDta.getUserid()\" because \"account\" is null"));
        verify(userRepository,never()).deleteById(any());
    }
}