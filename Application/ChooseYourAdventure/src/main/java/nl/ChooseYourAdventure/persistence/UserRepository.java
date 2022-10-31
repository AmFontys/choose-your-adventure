package nl.ChooseYourAdventure.persistence;

import nl.ChooseYourAdventure.model.Entity.User;
import nl.ChooseYourAdventure.model.dta.UserDta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);


}
