package nl.chooseyouradventure.persistence;

import nl.chooseyouradventure.model.entity.StoryBodyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorybodyTypeRepository extends JpaRepository<StoryBodyType,Integer> {


    StoryBodyType findByTypename(String typename);
}
