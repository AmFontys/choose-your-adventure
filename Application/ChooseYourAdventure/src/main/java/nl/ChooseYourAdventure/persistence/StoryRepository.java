package nl.ChooseYourAdventure.persistence;

import nl.ChooseYourAdventure.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story,Integer> {
}