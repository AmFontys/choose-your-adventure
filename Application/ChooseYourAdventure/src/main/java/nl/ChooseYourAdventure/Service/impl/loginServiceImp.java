package nl.ChooseYourAdventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.Service.loginService;
import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class loginServiceImp implements loginService {

    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(UserDta account) {
        User user =
                User.builder()
                        .userid(account.getUserid())
                        .username(account.getUsername())
                        .email(account.getEmail())
                        .password(passwordEncoder.encode(account.getPassword()))
                        .keyword(account.getKeyword())
                        .ismod(false)
                        .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public UserDta loginUser(String username, String password) {
        User userMail = userRepository.findByUsername(username);
        if (passwordEncoder.matches(password, userMail.getPassword())) {
            User logedinUser = userRepository.findByUsernameAndPassword(username, userMail.getPassword());
            return UserDta.builder()
                    .userid(logedinUser.getUserid())
                    .username(logedinUser.getUsername())
                    .email(logedinUser.getEmail())
                    .password(logedinUser.getPassword())
                    .keyword(logedinUser.getKeyword())
                    .ismod(logedinUser.getIsmod())
                    .build();
        } else return null;
    }
}

