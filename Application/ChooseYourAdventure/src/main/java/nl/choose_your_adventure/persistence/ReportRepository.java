package nl.choose_your_adventure.persistence;

import nl.choose_your_adventure.model.Entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
}
