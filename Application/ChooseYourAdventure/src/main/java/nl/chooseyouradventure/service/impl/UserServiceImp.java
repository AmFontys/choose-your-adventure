package nl.chooseyouradventure.service.impl;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.service.UserService;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {
    UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDta account) {
        Optional<User> user = userRepository.findById(account.getUserid());
        if (user.isPresent() && user.get().getUserid() > 0) {
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
        return null;
    }

    @Override
    public Optional<String> deleteUser(UserDta account) {
        try {
            if (account.getUserid() > 0) {
                userRepository.deleteById(account.getUserid());
            return Optional.of("Succesfull");
            }
        }
        catch(Exception exception){
            return Optional.ofNullable(exception.getMessage());
        }
        return Optional.of("This id doesn't exist");
    }
}
