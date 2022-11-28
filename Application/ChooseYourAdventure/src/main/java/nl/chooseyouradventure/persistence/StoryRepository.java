package nl.chooseyouradventure.persistence;

import nl.chooseyouradventure.model.dta.StoryBodyTypeDta;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Repository
public interface StoryRepository extends JpaRepository<Story,Integer> {

    void deleteByUser(User user);

    Iterable<? extends Story> findAllByUser(User deleteAccount);

    List<Story> findAllByUserUserid(Integer id);

    List<Story> findAllByTitleContaining( String name);
}
