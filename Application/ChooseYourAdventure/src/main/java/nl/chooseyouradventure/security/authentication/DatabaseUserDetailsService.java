package nl.chooseyouradventure.security.authentication;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public
class DatabaseUserDetailsService implements UserDetailsService {

    private static UserRepository userRepository;
    private static UserDetailsMapper userDetailsMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            Optional<User> userCredentials = userRepository.findByUsername(username);
        return userCredentials.map(userDetailsMapper::toUserDetails).orElse(null);

    }

}

