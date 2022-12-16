package nl.chooseyouradventure.service;

import nl.chooseyouradventure.security.encoding.AccessTokenEncoder;
import nl.chooseyouradventure.service.impl.LoginServiceImp;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AccessTokenEncoder accessTokenEncoder;

    private final PasswordEncoder passwordEncoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return "passwordEncoded";
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return (rawPassword.equals("passwordEncoded") & encodedPassword.equals( "passwordEncoded") )|| (rawPassword.equals("wrongPasswordEncoded") & encodedPassword.equals("wrongPasswordEncoded")) ;
        }
    };

    @InjectMocks
    private LoginServiceImp loginService  = new LoginServiceImp(userRepository,passwordEncoder,accessTokenEncoder);

    private User user;
    @BeforeEach
    void setup(){
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        loginService = new LoginServiceImp(userRepository,passwordEncoder,accessTokenEncoder);
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
    void givenUserObject_when_login_thenReturnUserObject(){
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
        assertThat(actualUser.getUsername()).isEqualTo(loggedinUser.get().getUsername());
        //verify
    }

    @Test
    void givenNoUserObject_when_login_thenReturnNothing(){
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        UserDta actualUser = loginService.loginUser(null,null);

        // then - verify the output
        assertThat(actualUser).isNull();
    }
    @Test
    void givenNoExistingUserObject_when_login_thenReturnNothing(){
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        UserDta actualUser = loginService.loginUser(user.getUsername(),user.getPassword());

        // then - verify the output
        assertThat(actualUser).isNull();
    }

    @Test
    void givenNotSimilarEncodingUserPassword_when_login_thenReturnNothing(){
        // given - precondition or setup
        Optional<User> loggedinUser = Optional.ofNullable(User.builder()
                .userid(200)
                .username("Ramen")
                .password("wrongPassword")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build());
        given(userRepository.findByUsername(user.getUsername())).willReturn(loggedinUser);
        // when -  action or the behaviour that we are going test
        UserDta actualUser = loginService.loginUser(user.getUsername(),user.getPassword());

        // then - verify the output
        assertThat(actualUser).isNull();
    }

    @Test
    void givenNotSimilarUserPassword_when_login_thenReturnNothing(){
        // given - precondition or setup
        Optional<User> loggedinUser = Optional.ofNullable(User.builder()
                .userid(200)
                .username("Ramen")
                .password("wrongPasswordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build());
        given(userRepository.findByUsername(user.getUsername())).willReturn(loggedinUser);
        // when -  action or the behaviour that we are going test
        UserDta actualUser = loginService.loginUser(user.getUsername(),user.getPassword());

        // then - verify the output
        assertThat(actualUser).isNull();
    }

    @Test
    void givenNoUserInDB_when_login_thenReturnNothing(){
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

        // when -  action or the behaviour that we are going test
        UserDta actualUser = loginService.loginUser(user.getUsername(),user.getPassword());

        // then - verify the output
        assertThat(actualUser).isNull();
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