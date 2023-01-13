package nl.chooseyouradventure.persistence;

import nl.chooseyouradventure.model.entity.Storybody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StorybodyRepository extends JpaRepository<Storybody,Integer> {


    List<Storybody> findAllByStoryStoryid(Integer storyid);
@Modifying
@Query(value = "UPDATE storybody SET chosen=:lastChosenNmb WHERE id=:optionId ",nativeQuery = true)
    void addOneToChosen(@Param("lastChosenNmb") Integer lastChosenNmb, @Param("optionId") Integer optionId);
}
