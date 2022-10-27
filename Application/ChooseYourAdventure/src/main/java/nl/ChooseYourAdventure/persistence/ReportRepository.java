package nl.ChooseYourAdventure.persistence;

import nl.ChooseYourAdventure.model.Report;
import nl.ChooseYourAdventure.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
}
