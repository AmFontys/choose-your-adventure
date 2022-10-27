package nl.ChooseYourAdventure.security.authentication;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public
class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            nl.ChooseYourAdventure.model.User userCredentials = userRepository.findByUsername(username);
            return userDetailsMapper.toUserDetails(userCredentials);

    }
}

