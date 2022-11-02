package nl.choose_your_adventure.Service;

import nl.choose_your_adventure.model.Entity.User;
import nl.choose_your_adventure.model.dta.UserDta;

public interface loginService {
    User saveUser(UserDta user);

    UserDta loginUser(String username, String password);
}
