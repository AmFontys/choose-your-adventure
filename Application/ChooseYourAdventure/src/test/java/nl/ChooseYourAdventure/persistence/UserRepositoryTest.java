package nl.ChooseYourAdventure.persistence;

import nl.ChooseYourAdventure.model.Entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByUsername() {
        // given
        String username = "user";

        // when
        User userCredentials = userRepository.findByUsername(username);

        // then
        assertThat(userCredentials).isNotNull();
    }

    @Test
    void findByUsernameAndPassword() {
    }
}