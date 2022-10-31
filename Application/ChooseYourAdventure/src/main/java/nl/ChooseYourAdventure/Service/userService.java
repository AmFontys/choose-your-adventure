package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;

import java.util.List;
import java.util.Optional;

public interface userService {
    List<User> getUsers();
    User updateUser(UserDta account);
    void deleteUser(UserDta account);
}
