package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.model.dta.UserDta;

public interface LoginService {
    User saveUser(UserDta user);

    UserDta loginUser(String username, String password);
}
