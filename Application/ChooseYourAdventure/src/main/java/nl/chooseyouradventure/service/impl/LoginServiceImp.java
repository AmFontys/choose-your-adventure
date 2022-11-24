package nl.chooseyouradventure.service.impl;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.security.authentication.AccessToken;
import nl.chooseyouradventure.security.encoding.AccessTokenDecoder;
import nl.chooseyouradventure.security.encoding.AccessTokenEncoder;
import nl.chooseyouradventure.service.LoginService;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginServiceImp implements LoginService {

    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

    private final AccessTokenEncoder accessTokenEncoder;

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
                            .accesToken(generateAccesToken(logedinUser))
                            .build();
                }
            } return null;
        }
        else return null;
    }

    private String generateAccesToken(Optional<User> logedinUser) {
        return accessTokenEncoder.encode(
                AccessToken.builder()
                        .subject(logedinUser.get().getUsername())
                        .roles(returnRole(logedinUser.get().getIsmod()))
                        .userId(logedinUser.get().getUserid())
        .build());
    }

    private List<String> returnRole(Boolean ismod) {
        if(ismod)return Collections.singletonList("MOD");
        return Collections.singletonList("USER");
    }
}

