package nl.choose_your_adventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.choose_your_adventure.Service.loginService;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;
import nl.choose_your_adventure.persistence.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        if(userRepository.findByUsername(user.getUsername()).isEmpty()) {
            userRepository.save(user);
            return user;
        }
        else return null;
    }

    @Override
    public UserDta loginUser(String username, String password) {
        Optional<User> userMail = userRepository.findByUsername(username);
        if (userMail.isPresent()) {
            if (passwordEncoder.matches(password, userMail.get().getPassword())) {
               Optional<User> logedinUser = userRepository.findByUsernameAndPassword(username, userMail.get().getPassword());
                if(logedinUser.isPresent()) {
                    return UserDta.builder()
                            .userid(logedinUser.get().getUserid())
                            .username(logedinUser.get().getUsername())
                            .email(logedinUser.get().getEmail())
                            .password(logedinUser.get().getPassword())
                            .keyword(logedinUser.get().getKeyword())
                            .ismod(logedinUser.get().getIsmod())
                            .build();
                }
            } return null;
        }
        else return null;
    }
}

