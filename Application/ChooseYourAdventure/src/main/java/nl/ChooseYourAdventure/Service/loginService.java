package nl.ChooseYourAdventure.Service;

import nl.ChooseYourAdventure.model.User;

public interface loginService {
    User saveUser(User user);

    User loginUser(String username, String password);
}
