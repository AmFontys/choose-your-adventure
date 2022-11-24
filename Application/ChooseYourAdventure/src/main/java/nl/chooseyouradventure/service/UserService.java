package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();
    User updateUser(UserDta account);
    Optional<String> deleteUser(UserDta account);
}
