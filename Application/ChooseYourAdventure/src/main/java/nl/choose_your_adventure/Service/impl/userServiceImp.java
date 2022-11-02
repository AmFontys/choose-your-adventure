package nl.choose_your_adventure.Service.impl;

import lombok.AllArgsConstructor;
import nl.choose_your_adventure.Service.userService;
import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;
import nl.choose_your_adventure.persistence.UserRepository;
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
        if (user.isPresent()) {
            if (user.get().getUserid() > 0) {
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
        }
        return null;
    }

    @Override
    public void deleteUser(UserDta account) {
        userRepository.deleteById(account.getUserid());
    }
}
