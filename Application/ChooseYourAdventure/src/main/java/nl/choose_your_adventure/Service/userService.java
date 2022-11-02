package nl.choose_your_adventure.Service;

import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;

import java.util.List;

public interface userService {
    List<User> getUsers();
    User updateUser(UserDta account);
    void deleteUser(UserDta account);
}
