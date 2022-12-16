package nl.chooseyouradventure.persistence;

import nl.chooseyouradventure.model.entity.Report;
import nl.chooseyouradventure.model.entity.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportTypeRepository  extends JpaRepository<ReportType,Integer> {
    ReportType findByType(String type);
}
