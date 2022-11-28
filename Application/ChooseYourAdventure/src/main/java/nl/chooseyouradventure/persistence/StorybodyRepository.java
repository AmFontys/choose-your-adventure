package nl.chooseyouradventure.persistence;

import nl.chooseyouradventure.model.entity.Storybody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorybodyRepository extends JpaRepository<Storybody,Integer> {


    List<Storybody> findAllByStoryStoryid(Integer storyid);
}
