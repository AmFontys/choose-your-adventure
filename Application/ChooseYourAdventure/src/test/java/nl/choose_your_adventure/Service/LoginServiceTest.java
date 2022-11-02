package nl.choose_your_adventure.Service;

import nl.choose_your_adventure.Service.impl.loginServiceImp;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;
import nl.choose_your_adventure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return "passwordEncoded";
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.equals("passwordEncoded") & encodedPassword.equals( "passwordEncoded");
        }
    };

    @InjectMocks
    private loginServiceImp loginService  = new loginServiceImp(userRepository,passwordEncoder);

    private User user;
    @BeforeEach
    public void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        loginService = new loginServiceImp(userRepository,passwordEncoder);
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
    public void givenUserObject_when_login_thenReturnUserObject(){
        // given - precondition or setup
       Optional<User> loggedinUser = Optional.ofNullable(User.builder()
               .userid(200)
               .username("Ramen")
               .password("passwordEncoded")
               .keyword("key")
               .email("ramen@gmail.com")
               .ismod(false)
               .build());

        given(userRepository.findByUsername(user.getUsername())).willReturn(loggedinUser);
        given(userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword())).willReturn(loggedinUser);
        // when -  action or the behaviour that we are going test
UserDta actualUser = loginService.loginUser(user.getUsername(),user.getPassword());

        // then - verify the output
        assertThat(actualUser).isNotNull();
    }


    @Test
    void saveNewUser() {
        // given - precondition or setup
        UserDta registerUserDta = UserDta.builder()
                .username("Spaghet")
                .password("1234")
                .keyword("key")
                .email("spaghet@gmail.com")
                .build();

        given(userRepository.findByUsername(registerUserDta.getUsername())).willReturn(Optional.empty());
        // when -  action or the behaviour that we are going test
        User actualUser = loginService.saveUser(registerUserDta);
        // then - verify the output
        assertThat(actualUser).isNotNull();
    }
    @Test
    void saveUser_withUsedUsername_returnNull() {
        // given - precondition or setup
        UserDta registerUser = UserDta.builder()
                .username("Ramen")
                .password("1234")
                .keyword("key")
                .email("spaghet@gmail.com")
                .build();

        given(userRepository.findByUsername("Ramen")).willReturn(Optional.ofNullable(user));

        // when -  action or the behaviour that we are going test
        User actualUser = loginService.saveUser(registerUser);
        // then - verify the output
        assertThat(actualUser).isNull();
    }
}