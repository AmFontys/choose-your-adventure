package nl.ChooseYourAdventure.security.authentication;

import nl.ChooseYourAdventure.model.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserDetailsMapperTest {

    private UserDetailsMapper userDetailsMapper = new UserDetailsMapper();

    @Test
    void toUserDetails() {
        // given
        User userCredentials =
                User.builder()
                        .password("password")
                        .username("user")
                        .ismod(false)
                        .build();

        // when
        UserDetails userDetails = userDetailsMapper.toUserDetails(userCredentials);

        // then
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.isEnabled()).isTrue();
    }
}