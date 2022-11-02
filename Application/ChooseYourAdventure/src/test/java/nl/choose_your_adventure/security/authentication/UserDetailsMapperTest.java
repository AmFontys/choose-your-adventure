package nl.choose_your_adventure.security.authentication;

import nl.choose_your_adventure.model.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDetailsMapperTest {

    private final UserDetailsMapper userDetailsMapper = new UserDetailsMapper();

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