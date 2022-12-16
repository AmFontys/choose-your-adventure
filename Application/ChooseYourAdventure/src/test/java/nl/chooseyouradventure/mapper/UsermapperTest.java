package nl.chooseyouradventure.mapper;

import nl.chooseyouradventure.model.Usermapper;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UsermapperTest {

    User entity;
    UserDta dta;

    @BeforeEach
    void setUp() {
        entity= User.builder()
                .userid(200)
                .username("Ramen")
                .password("passwordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build();

        dta= UserDta.builder()
                .userid(200)
                .username("Ramen")
                .password("passwordEncoded")
                .keyword("key")
                .email("ramen@gmail.com")
                .ismod(false)
                .build();
    }

    @Test
    void giveEntity_successful() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test
        User actualEntity=Usermapper.giveEntity(dta);
        //then - verify the output
        assertThat(actualEntity).isEqualTo(entity);
    }
    @Test
    void giveEntity_nullGiven() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test
        User actualEntity=Usermapper.giveEntity(null);
        //then - verify the output
        assertThat(actualEntity).isNull();
    }
    @Test
    void giveEntity_noEmail() {
        //given - precondition or setup
dta.setEmail(null);
        //when - action or the behaviour that we are going test
        User actualEntity= Usermapper.giveEntity(null);
        //then - verify the output
        assertThat(actualEntity).isNull();
    }

    @Test
    void giveDta_succesful() {
        //given - precondition or setup

        //when - action or the behaviour that we are going test
UserDta actualDta = Usermapper.giveDta(entity);
        //then - verify the output
        assertThat(actualDta).isEqualTo(dta);
    }

    @Test
    void giveDta_NullEntered() {
        //given - precondition or setup
entity=null;
        //when - action or the behaviour that we are going test
        UserDta actualDta = Usermapper.giveDta(entity);
        //then - verify the output
        assertThat(actualDta).isNull();
    }

    @Test
    void giveDta_noEmail() {
        //given - precondition or setup
        entity.setEmail(null);
        //when - action or the behaviour that we are going test
        UserDta actualDta = Usermapper.giveDta(entity);
        //then - verify the output
        assertThat(actualDta).isNotEqualTo(dta);
        assertThat(actualDta.getEmail()).isEqualTo(null);
    }
}