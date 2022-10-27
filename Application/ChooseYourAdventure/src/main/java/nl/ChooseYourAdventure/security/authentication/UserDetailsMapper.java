package nl.ChooseYourAdventure.security.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public
class UserDetailsMapper {

    public UserDetails toUserDetails(nl.ChooseYourAdventure.model.User userCredentials) {

        return User.withUsername(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles(getRole( userCredentials.getIsmod()))
                .build();
    }

    String getRole(Boolean isMod){
        if(isMod) return "ROLE_MOD";
        else return "ROLE_USER";
    }
}
