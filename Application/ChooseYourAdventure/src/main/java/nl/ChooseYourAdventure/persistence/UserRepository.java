package nl.ChooseYourAdventure.persistence;

import nl.ChooseYourAdventure.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmailAndPassword(String email, String password);

    User findByUsername(String username);

    User findByPassword(String password);


    User findByUsernameAndPassword(String username, String password);
}
