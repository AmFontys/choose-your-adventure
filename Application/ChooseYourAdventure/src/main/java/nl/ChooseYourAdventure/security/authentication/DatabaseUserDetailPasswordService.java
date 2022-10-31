package nl.ChooseYourAdventure.security.authentication;


import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class DatabaseUserDetailPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    private final UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        User userCredentials = userRepository.findByUsername(user.getUsername());
        userCredentials.setPassword(newPassword);
        return userDetailsMapper.toUserDetails(userCredentials);
    }
}