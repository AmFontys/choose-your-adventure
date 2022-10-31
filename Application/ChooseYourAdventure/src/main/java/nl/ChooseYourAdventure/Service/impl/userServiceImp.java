package nl.ChooseYourAdventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.ChooseYourAdventure.Service.userService;
import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
import nl.ChooseYourAdventure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class userServiceImp implements userService {
    UserRepository userRepository;
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDta account) {
        Optional<User> user = userRepository.findById(account.getUserid());

        if(user.get().getUserid()>0) {
        User updateAccount = User.builder()
                .userid(account.getUserid())
                .username(account.getUsername())
                .email(account.getEmail())
                .password(account.getPassword())
                .keyword(account.getKeyword())
                .ismod(account.getIsmod())
                .build();

            return userRepository.save(updateAccount);
        }
        else return null;
    }

    @Override
    public void deleteUser(UserDta account) {
        userRepository.deleteById(account.getUserid());
    }
}
