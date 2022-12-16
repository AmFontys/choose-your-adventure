package nl.chooseyouradventure.service;

import nl.chooseyouradventure.model.dta.ReportDta;
import nl.chooseyouradventure.model.dta.ReportTypeDta;

import java.util.List;

public interface ReportService {
    String saveReport(ReportDta reportDta);

    List<ReportDta> getAllReports();

    ReportDta getOneReport(Integer id);

    List<ReportTypeDta> getAllReportTypes();

    ReportTypeDta getReportType(String type);

    String deleteReport(Integer id);

    void updateReport(ReportDta reportDta);
}
