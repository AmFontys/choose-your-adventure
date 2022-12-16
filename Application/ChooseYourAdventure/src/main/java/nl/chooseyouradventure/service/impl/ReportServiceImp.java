package nl.chooseyouradventure.service.impl;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.ReportMapper;
import nl.chooseyouradventure.model.dta.ReportDta;
import nl.chooseyouradventure.model.dta.ReportTypeDta;
import nl.chooseyouradventure.persistence.ReportRepository;
import nl.chooseyouradventure.persistence.ReportTypeRepository;
import nl.chooseyouradventure.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportServiceImp implements ReportService {


    ReportRepository reportRepository;

    ReportTypeRepository reportTypeRepository;

    ReportMapper reportMapping;

    @Override
    public String saveReport(ReportDta reportDta) {
        reportRepository.save(reportMapping.getReportEntity(reportDta));
        return "New Report added";
    }

    @Override
    public List<ReportDta> getAllReports() {

        return reportMapping.getReportDta(reportRepository.findAll());
    }

    @Override
    public ReportDta getOneReport(Integer id) {
        if(id<1)return null;
        return reportMapping.getReportDta(reportRepository.findById(id));
    }

    @Override
    public ReportTypeDta getReportType(String type){
        return reportMapping.giveDtaReportType(reportTypeRepository.findByType(type));
    }

    @Override
    public List<ReportTypeDta> getAllReportTypes() {
        return reportMapping.giveDtaReportType(reportTypeRepository.findAll());
    }



    @Override
    public String deleteReport(Integer id) {
        if(id<1)return "unsuccesfull";
        reportRepository.deleteById(id);
        return "succesfull";
    }

    @Override
    public void updateReport(ReportDta reportDta) {
        Optional<ReportDta> checkReport = Optional.ofNullable(reportMapping.getReportDta(reportRepository.findById(reportDta.getReportid())));
        if(checkReport.isPresent() && checkReport.get().getReportid()>0) {
            reportRepository.save(reportMapping.getReportEntity(reportDta));
        }
    }
}
