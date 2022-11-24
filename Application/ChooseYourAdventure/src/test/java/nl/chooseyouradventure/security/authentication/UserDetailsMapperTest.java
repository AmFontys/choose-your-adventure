package nl.chooseyouradventure.security.authentication;

import nl.chooseyouradventure.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDetailsMapperTest {

    private final UserDetailsMapper userDetailsMapper = new UserDetailsMapper();

    @Test
    void toUserDetails_IsUser() {
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
        assertThat(userDetails.getAuthorities().toString()).isEqualTo("[ROLE_USER]");
    }

    @Test
    void toUserDetails_IsMod() {
        // given
        User userCredentials =
                User.builder()
                        .password("password")
                        .username("user")
                        .ismod(true)
                        .build();

        // when
        UserDetails userDetails = userDetailsMapper.toUserDetails(userCredentials);

        // then
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities().toString()).isEqualTo("[ROLE_MOD]");
    }
    @Test
    void toUserDetails_NoRole() {
        // given
        User userCredentials =
                User.builder()
                        .password("password")
                        .username("user")
                        .build();

        // when
        UserDetails userDetails = userDetailsMapper.toUserDetails(userCredentials);

        // then
        assertThat(userDetails.getUsername()).isEqualTo("user");
        assertThat(userDetails.getPassword()).isEqualTo("password");
        assertThat(userDetails.isEnabled()).isTrue();
        assertThat(userDetails.getAuthorities().toString()).isEqualTo("[ROLE_NONE]");
    }
}