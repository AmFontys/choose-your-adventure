package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;

public interface loginService {
    User saveUser(UserDta user);

    UserDta loginUser(String username, String password);
}
