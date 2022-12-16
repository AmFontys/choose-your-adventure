package nl.chooseyouradventure.controller;


import nl.chooseyouradventure.model.dta.ReportDta;
import nl.chooseyouradventure.model.dta.ReportTypeDta;
import nl.chooseyouradventure.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins =  {"http://localhost:3000/"})
public class ReportController {
    @Autowired
    private ReportService reportService;
    @PostMapping("")
    public String add(@RequestBody ReportDta reportDta){
        reportDta.setType(reportService.getReportType(reportDta.getType().getType()));

        return  reportService.saveReport(reportDta);
    }

    @GetMapping("")
    public List<ReportDta> getAllReports(){
        return reportService.getAllReports();
    }

    @GetMapping("{id}")
    public ReportDta getOneReport(@PathVariable Integer id){
        return reportService.getOneReport(id);
    }

    @GetMapping("/type")
    public List<ReportTypeDta> getAllReportTypes(){
        return reportService.getAllReportTypes();
    }

    @DeleteMapping("{id}")
    public String deleteReport(@PathVariable Integer id){
        return reportService.deleteReport(id);
    }

    @PutMapping("")
    public void UpdateReport(@RequestBody ReportDta reportDta){
        reportService.updateReport(reportDta);
    }

}
