package nl.ChooseYourAdventure.security.authentication;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
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

            User userCredentials = userRepository.findByUsername(username);
            return userDetailsMapper.toUserDetails(userCredentials);

    }
}

