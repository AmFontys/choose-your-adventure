package nl.chooseyouradventure.security.authentication;


import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        Optional<User> userCredentials = userRepository.findByUsername(user.getUsername());
        if(userCredentials.isPresent()) {
            userCredentials.get().setPassword(newPassword);
            return userDetailsMapper.toUserDetails(userCredentials.get());
        }
        else return null;
    }
}