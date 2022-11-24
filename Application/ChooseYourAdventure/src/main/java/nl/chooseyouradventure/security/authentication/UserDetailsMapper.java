package nl.chooseyouradventure.security.authentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public
class UserDetailsMapper {

    public UserDetails toUserDetails(nl.chooseyouradventure.model.entity.User userCredentials) {
        return User.withUsername(userCredentials.getUsername())
                .password(userCredentials.getPassword())
                .roles(getRole( userCredentials.getIsmod()))
                .build();
    }

    String getRole(Boolean isMod){
        if(isMod==null)
            return "NONE";

        if(Boolean.TRUE.equals(isMod)) return "MOD";
        else return "USER";
    }
}
