package nl.chooseyouradventure.mapper;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReportMapperTest {

    @Autowired
    ReportMapper mapper;

    @BeforeEach
    void setUp() {
        mapper =new ReportMapper(new StoryMapper());
    }

    @Test
    void getReportEntity() {
        // given - precondition or setup
        UserDta userDta = UserDta.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        StoryDta storyDta = new StoryDta(10,userDta,"Fake story");
        ReportTypeDta typeDta = new ReportTypeDta(1,"Racism");

        ReportDta reportDta = ReportDta.builder()
                .reportid(9)
                .user(userDta)
                .story(storyDta)
                .type(typeDta)
                .reportText("Fake report")
                .build();

        // when -  action or the behaviour that we are going test
        Report actual = mapper.getReportEntity(reportDta);
        // then - verify the output
        assertThat(actual.getType().getType()).isEqualTo(reportDta.getType().getType());
        assertThat(actual.getReportText()).isEqualTo(reportDta.getReportText());
    }

    @Test
    void getReportDta() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type= new ReportType(1,"Racism");

        Report report = Report.builder()
                .reportid(9)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        // when -  action or the behaviour that we are going test
        ReportDta actual = mapper.getReportDta(Optional.ofNullable(report));
        // then - verify the output
        assertThat(actual.getReportid()).isNotNull();
        assertThat(actual.getType().getType()).isEqualTo(report.getType().getType());
        assertThat(actual.getReportText()).isEqualTo(report.getReportText());
    }

    @Test
    void getReportDtaList() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type= new ReportType(1,"Racism");

        Report report = Report.builder()
                .reportid(9)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        Report reportTwo = Report.builder()
                .reportid(19)
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report 2")
                .build();

        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        reportList.add(reportTwo);

        // when -  action or the behaviour that we are going test
        List<ReportDta> actual = mapper.getReportDta(reportList);
        // then - verify the output
        assertThat(actual).isNotNull()
                .hasSize(2);


        assertThat(actual.get(0).getType().getType()).isEqualTo(report.getType().getType());
        assertThat(actual.get(0).getReportText()).isEqualTo(report.getReportText());
        assertThat(actual.get(1).getType().getType()).isEqualTo(reportTwo.getType().getType());
        assertThat(actual.get(1).getReportText()).isEqualTo(reportTwo.getReportText());
    }

    @Test
    void giveDtaReportType() {
        //given - precondition or setup
        ReportType type= new ReportType(1,"Racism");
        // when -  action or the behaviour that we are going test
        ReportTypeDta actual = mapper.giveDtaReportType(type);
        // then - verify the output
        assertThat(actual.getReportTypeId()).isNotNull();
        assertThat(actual.getType()).isEqualTo(type.getType());

    }

    @Test
    void giveDtaReportTypeList() {
        //given - precondition or setup
        ReportType type= new ReportType(1,"Racism");
        ReportType typeTwo= new ReportType(2,"Unsafe link");

        List<ReportType> list = new ArrayList<>();
        list.add(type);
        list.add(typeTwo);

        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = mapper.giveDtaReportType(list);
        // then - verify the output
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getType()).isEqualTo(type.getType());
        assertThat(actual.get(1).getType()).isEqualTo(typeTwo.getType());
    }

    @Test
    void giveEntityReportType() {
        //given - precondition or setup
        ReportTypeDta type= new ReportTypeDta(1,"Racism");
        // when -  action or the behaviour that we are going test
        ReportType actual = mapper.giveEntityReportType(type);
        // then - verify the output
        assertThat(actual.getReportTypeId()).isNotNull();
        assertThat(actual.getType()).isEqualTo(type.getType());
    }

    //Missing some values
    @Test
    void getReportEntity_MissingIdReport() {
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

        // when -  action or the behaviour that we are going test
        Report actual = mapper.getReportEntity(reportDta);
        // then - verify the output
        assertThat(actual).isNull();
    }

    @Test
    void getReportDta_MissingIdReport() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type= new ReportType(1,"Racism");

        Report report = Report.builder()
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        // when -  action or the behaviour that we are going test
        ReportDta actual = mapper.getReportDta(Optional.ofNullable(report));
        // then - verify the output
        assertThat(actual).isNull();
    }

    @Test
    void getReportDtaList_MissingIdReport() {
        // given - precondition or setup
        User user = User.builder()
                .userid(100).username("Bob").email("bob@gmail.com").password("secret").keyword("Knan").ismod(false)
                .build();
        Story story = new Story(10,user,"Fake story");
        ReportType type= new ReportType(1,"Racism");

        Report report = Report.builder()
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report")
                .build();

        Report reportTwo = Report.builder()
                .user(user)
                .story(story)
                .type(type)
                .reportText("Fake report 2")
                .build();

        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        reportList.add(reportTwo);

        // when -  action or the behaviour that we are going test
        List<ReportDta> actual = mapper.getReportDta(reportList);
        // then - verify the output
        assertThat(actual).isNotNull()
                .hasSize(0);
    }

    @Test
    void giveDtaReportType_MissingId() {
        //given - precondition or setup
        ReportType type= ReportType.builder().type("Racism").build();
        // when -  action or the behaviour that we are going test
        ReportTypeDta actual = mapper.giveDtaReportType(type);
        // then - verify the output
        assertThat(actual).isNull();
    }

    @Test
    void giveDtaReportTypeList_MissingId() {
        //given - precondition or setup
        ReportType type= ReportType.builder().type("Racism").build();
        ReportType typeTwo= ReportType.builder().type("Unsafe link").build();

        List<ReportType> list = new ArrayList<>();
        list.add(type);
        list.add(typeTwo);

        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = mapper.giveDtaReportType(list);
        // then - verify the output
        assertThat(actual).hasSize(0).isEmpty();
    }

    @Test
    void giveEntityReportType_MissingId() {
        //given - precondition or setup
        ReportTypeDta type= ReportTypeDta.builder().type("Racism").build();
        // when -  action or the behaviour that we are going test
        ReportType actual = mapper.giveEntityReportType(type);
        // then - verify the output
        assertThat(actual).isNull();
    }

    //Is null or empty
    @Test
    void getReportEntity_IsNull() {
        // given - precondition or setup
       ReportDta reportDta = null;

        // when -  action or the behaviour that we are going test
        Report actual = mapper.getReportEntity(reportDta);
        // then - verify the output
        assertThat(actual).isNull();
    }

    @Test
    void getReportDta_IsNull() {
        // given - precondition or setup


        Report report = null;

        // when -  action or the behaviour that we are going test
        ReportDta actual = mapper.getReportDta(Optional.ofNullable(report));
        // then - verify the output
        assertThat(actual).isNull();

    }

    @Test
    void getReportDtaList_IsNull() {
        // given - precondition or setup

        List<Report> reportList = null;

        // when -  action or the behaviour that we are going test
        List<ReportDta> actual = mapper.getReportDta(reportList);
        // then - verify the output
        assertThat(actual).isEmpty();
    }
    @Test
    void getReportDtaList_ItemsAreNull() {
        // given - precondition or setup


        Report report = null;

        Report reportTwo = null;

        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        reportList.add(reportTwo);

        // when -  action or the behaviour that we are going test
        List<ReportDta> actual = mapper.getReportDta(reportList);
        // then - verify the output
        assertThat(actual).isEmpty();
    }
    @Test
    void giveDtaReportType_IsNull() {
        //given - precondition or setup
        ReportType type= null;
        // when -  action or the behaviour that we are going test
        ReportTypeDta actual = mapper.giveDtaReportType(type);
        // then - verify the output
        assertThat(actual).isNull();
    }

    @Test
    void giveDtaReportTypeList_ItemsAreNull() {
        //given - precondition or setup
        ReportType type= null;
        ReportType typeTwo= null;

        List<ReportType> list = new ArrayList<>();
        list.add(type);
        list.add(typeTwo);

        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = mapper.giveDtaReportType(list);
        // then - verify the output
        assertThat(actual).isEmpty();
    }
    @Test
    void giveDtaReportTypeList_IsNull() {
        //given - precondition or setup
        List<ReportType> list = null;

        // when -  action or the behaviour that we are going test
        List<ReportTypeDta> actual = mapper.giveDtaReportType(list);
        // then - verify the output
        assertThat(actual).isEmpty();
    }
    @Test
    void giveEntityReportType_IsNull() {
        //given - precondition or setup
        ReportTypeDta type= null;
        // when -  action or the behaviour that we are going test
        ReportType actual = mapper.giveEntityReportType(type);
        // then - verify the output
        assertThat(actual).isNull();
    }


}