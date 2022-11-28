package nl.chooseyouradventure.model;

import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Usermapper {
    public static User giveEntity(UserDta user){
        if(user==null)return null;
        return User.builder()
                .userid(user.getUserid())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .keyword(user.getKeyword())
                .ismod(user.getIsmod())
                .build();
    }

    public static UserDta giveDta(User user) {
        if(user==null) return null;
        return UserDta.builder()
                .userid(user.getUserid())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .keyword(user.getKeyword())
                .ismod(user.getIsmod())
                .build();
    }
}
