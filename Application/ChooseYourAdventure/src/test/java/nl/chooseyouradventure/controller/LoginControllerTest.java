package nl.chooseyouradventure.controller;

import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class LoginControllerTest {

  @Autowired
  private MockMvc mockMvc;



private User user;


  @MockBean
  private LoginService loginService;


  @BeforeEach
  void setup(){
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
    void register_expect201() throws Exception {
        UserDta sendUser = UserDta.builder()
                .username("Ramen")
                .password("passwordEncoded")
                .email("ramen@gmail.com")
                .build();

        when(loginService.saveUser(sendUser)).thenReturn(user);

        mockMvc.perform(post("/api/register")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                            {"username":"Ramen","email":"ramen@gmail.com","password":"passwordEncoded"}
                            """))
                .andDo(print())
                .andExpect(status().isCreated())
        ;

        verify(loginService).saveUser(sendUser);

    }
    @Test
    void register_expect400() throws Exception {
        UserDta sendUser = UserDta.builder()
                .username("Ramen")
                .password("passwordEncoded")
                .email("ramen@gmail.com")
                .build();

        when(loginService.saveUser(sendUser)).thenReturn(user);

        mockMvc.perform(post("/api/registering")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content("""
                            {"username":"Ramen","email":"ramen@gmail.com","password":"passwordEncoded"}
                            """))
                .andDo(print())
                .andExpect(status().is4xxClientError())
        ;

        verifyNoInteractions(loginService);

    }

    @Test
    void getLogin_expect200() throws Exception{
UserDta userDta= UserDta.builder()
        .userid(200)
        .username("Ramen")
        .password("passwordEncoded")
        .keyword("key")
        .email("ramen@gmail.com")
        .ismod(false)
        .build();

    when(loginService.loginUser(user.getUsername(),user.getPassword())).thenReturn(userDta);

    mockMvc.perform(get("/api/login").contentType(APPLICATION_JSON_VALUE)
            .param("username","Ramen")
                    .param("password","passwordEncoded"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
            .andExpect(content().json("""
              {"userid":200,"username":"Ramen","password": "passwordEncoded","keyword": "key","email": "ramen@gmail.com","ismod": false}
"""))
;

    verify(loginService).loginUser("Ramen","passwordEncoded");
    }
}