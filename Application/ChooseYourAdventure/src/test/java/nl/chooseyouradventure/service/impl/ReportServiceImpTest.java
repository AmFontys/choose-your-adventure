package nl.chooseyouradventure.service.impl;

import nl.chooseyouradventure.model.ReportMapper;
import nl.chooseyouradventure.model.StoryMapper;
import nl.chooseyouradventure.model.dta.ReportDta;
import nl.chooseyouradventure.model.dta.ReportTypeDta;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.Report;
import nl.chooseyouradventure.model.entity.ReportType;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import nl.chooseyouradventure.persistence.ReportRepository;
import nl.chooseyouradventure.persistence.ReportTypeRepository;
import nl.chooseyouradventure.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ReportServiceImpTest {

    ReportMapper mapper;
    @InjectMocks
    ReportService service;

    @Mock
    ReportRepository reportRepository;
    @Mock
    ReportTypeRepository typeRepository;

    @BeforeEach
    void setUp() {
        reportRepository = Mockito.mock(ReportRepository.class);
        typeRepository = Mockito.mock(ReportTypeRepository.class);
        mapper = new ReportMapper(new StoryMapper());
        service = new ReportServiceImp(reportRepository,typeRepository, mapper);
    }

    @Test
    void saveReport() {
        // given - precondition or setup
        UserDta userDta = UserDta.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        StoryDta storyDta = new StoryDta(10,userDta,"Fake story");
        ReportTypeDta typeDta = new ReportTypeDta(1,"Racism");

        ReportDta reportDta = ReportDta.builder()
                .user(userDta)
                .story(storyDta)
                .type(typeDta)
                .reportText("Fake report")
                .build();

        Report report = mapper.getReportEntity(reportDta);
        Report returnReport = report;

        given(reportRepository.save(report)).willReturn(returnReport);
        // when -  action or the behaviour that we are going test
       String response = service.saveReport(reportDta);
        // then - verify the output
        assertThat(response).isEqualTo("New Report added");
        verify(reportRepository).save(any());
    }

    @Test
    void getAllReports() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type = new ReportType(1,"Racism");

        Report report = Report.builder()
                .reportid(9)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        Report reportTwo = Report.builder()
                .reportid(9)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report Two")
                .build();

        List<Report> list = new ArrayList<>();
        list.add(reportTwo);
        list.add(report);

        given(reportRepository.findAll()).willReturn(list);
        // when -  action or the behaviour that we are going test
       List<ReportDta> returnList = service.getAllReports();
        // then - verify the output
        assertThat(returnList).isNotEmpty().hasSize(2);
        verify(reportRepository).findAll();
    }

    @Test
    void getOneReport() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type = new ReportType(1,"Racism");

        Report report = Report.builder()
                .reportid(9)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        given(reportRepository.findById(9)).willReturn(Optional.ofNullable(report));
        // when -  action or the behaviour that we are going test
        ReportDta returnItem = service.getOneReport(9);
        // then - verify the output
        assertThat(returnItem.getReportid()).isEqualTo(9);
        assertThat(returnItem.getReportText()).isEqualTo("Fake report");
        verify(reportRepository).findById(any());

    }

    @Test
    void getReportType() {
        //given - precondition or setup
        ReportType type= new ReportType(1,"Racism");

        given(typeRepository.findByType("Racism")).willReturn(type);
        // when -  action or the behaviour that we are going test
        ReportTypeDta actual = service.getReportType("Racism");
        // then - verify the output
        assertThat(actual.getReportTypeId()).isNotNull();
        assertThat(actual).isEqualTo(mapper.giveDtaReportType(type));
        verify(typeRepository).findByType("Racism");
    }

    @Test
    void getAllReportTypes() {
        //given - precondition or setup
        ReportType type= new ReportType(1,"Racism");

        List<ReportType> list = new ArrayList<>();
        list.add(type);

        given(typeRepository.findAll()).willReturn(list);
        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = service.getAllReportTypes();
        // then - verify the output
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0)).isEqualTo(mapper.giveDtaReportType(type));
        verify(typeRepository).findAll();
    }

    @Test
    void deleteReport() {
        //given - precondition or setup

        // when -  action or the behaviour that we are going test
        String actual = service.deleteReport(10);
        // then - verify the output
        assertThat(actual).isEqualTo("succesfull");
        verify(reportRepository).deleteById(10);
    }

    @Test
    void updateReport() {
        //given - precondition or setup
        UserDta userDta = UserDta.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        StoryDta storyDta = new StoryDta(10,userDta,"Fake story");
        ReportTypeDta typeDta = new ReportTypeDta(1,"Racism");

        ReportDta reportDtaOrginal = ReportDta.builder()
                .reportid(8)
                .user(userDta)
                .story(storyDta)
                .type(typeDta)
                .reportText("Fake report")
                .build();

        ReportDta reportDtaUpdate = ReportDta.builder()
                .reportid(8)
                .user(userDta)
                .story(storyDta)
                .type(typeDta)
                .reportText("Fake report")
                .build();

        Report reportOrginal = mapper.getReportEntity(reportDtaOrginal);
        Report returnUpdate = mapper.getReportEntity(reportDtaUpdate);

        given(reportRepository.findById(reportOrginal.getReportid())).willReturn(Optional.ofNullable(reportOrginal));
        given(reportRepository.save(mapper.getReportEntity(reportDtaUpdate))).willReturn(returnUpdate);
        // when -  action or the behaviour that we are going test
        service.updateReport(reportDtaUpdate);
        // then - verify the output
        verify(reportRepository).findById(8);
        verify(reportRepository).save(any());
    }

    //given no value
    @Test
    void saveReport_GivenNull() {
        // given - precondition or setup

        ReportDta reportDta = null;


        // when -  action or the behaviour that we are going test
        String response = service.saveReport(reportDta);
        // then - verify the output
        assertThat(response).isEqualTo("Report not added");
        verify(reportRepository,never()).save(any());
    }

    @Test
    void getAllReports_GivenNullList() {
        // given - precondition or setup
        List<Report> list = null;


        given(reportRepository.findAll()).willReturn(list);
        // when -  action or the behaviour that we are going test
        List<ReportDta> returnList = service.getAllReports();
        // then - verify the output
        assertThat(returnList).isEmpty();
        verify(reportRepository).findAll();
    }

    @Test
    void getOneReport_GivenNull() {
        // given - precondition or setup
        Report report = null;

        given(reportRepository.findById(9)).willReturn(Optional.ofNullable(report));
        // when -  action or the behaviour that we are going test
        ReportDta returnItem = service.getOneReport(9);
        // then - verify the output
        assertThat(returnItem).isNull();
        verify(reportRepository).findById(any());

    }

    @Test
    void getReportType_GivenNull() {
        //given - precondition or setup

        // when -  action or the behaviour that we are going test
        ReportTypeDta actual = service.getReportType(null);
        // then - verify the output
        assertThat(actual).isNull();
        verify(typeRepository,never()).findByType(any());
    }

    @Test
    void getAllReportTypes_GivenNullList() {
        //given - precondition or setup

        List<ReportType> list = null;


        given(typeRepository.findAll()).willReturn(list);
        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = service.getAllReportTypes();
        // then - verify the output
        assertThat(actual).isEmpty();
        verify(typeRepository).findAll();
    }

    @Test
    void deleteReport_GivenNull() {
        //given - precondition or setup

        // when -  action or the behaviour that we are going test
        String actual = service.deleteReport(null);
        // then - verify the output
        assertThat(actual).isEqualTo("unsuccesfull");
        verify(reportRepository,never()).deleteById(any());
    }

    @Test
    void updateReport_GivenNull() {
        //given - precondition or setup
        ReportDta reportDta = null;
        // when -  action or the behaviour that we are going test
        service.updateReport(reportDta);
        // then - verify the output
        verify(reportRepository,never()).findById(any());
        verify(reportRepository,never()).save(any());
    }
}