package nl.ChooseYourAdventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.Service.loginService;
import nl.ChooseYourAdventure.model.User;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class loginServiceImp implements loginService {

    private UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;



    @Override
    public  User saveUser(User account){ User user =
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
    public User loginUser(String username, String password) {
        //password=$2a$14$jUgqxYs.QNptzb8Oj/6uf.0OJRZ0a.J9DWW3r1dWTuzqlup.XMODe
        User userMail= userRepository.findByUsername(username);
        if(passwordEncoder.matches(password,userMail.getPassword()))
        return userRepository.findByUsernameAndPassword(username,userMail.getPassword());
        else return null;
    }
}
