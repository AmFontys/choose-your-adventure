package nl.chooseyouradventure.model;

import lombok.AllArgsConstructor;
import nl.chooseyouradventure.model.dta.ReportDta;
import nl.chooseyouradventure.model.dta.ReportTypeDta;
import nl.chooseyouradventure.model.dta.StoryDta;
import nl.chooseyouradventure.model.dta.UserDta;
import nl.chooseyouradventure.model.entity.Report;
import nl.chooseyouradventure.model.entity.ReportType;
import nl.chooseyouradventure.model.entity.Story;
import nl.chooseyouradventure.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@AllArgsConstructor
public class ReportMapper {

    StoryMapper storyMapper;
    public Report getReportEntity(ReportDta reportDta) {
        Story tempStory =StoryMapper.giveEntityStory(reportDta.getStory());
        ReportType tempReportType = giveEntityReportType(reportDta.getType());
        User tempUser = Usermapper.giveEntity(reportDta.getUser());

        return Report.builder()
                .reportid(reportDta.getReportid())
                .reportText(reportDta.getReportText())
                .story(tempStory)
                .type(tempReportType)
                .user(tempUser)
                .build();
    }


    public List<ReportDta> getReportDta(List<Report> all) {
        List<ReportDta> arrayList = new ArrayList<>();
        for(Report item : all ) {
            StoryDta tempStory = storyMapper.giveDtaStory(item.getStory());
            ReportTypeDta tempReportType = giveDtaReportType(item.getType());
            UserDta tempUser = Usermapper.giveDta(item.getUser());

            arrayList.add(
            ReportDta.builder()
                    .reportid(item.getReportid())
                    .reportText(item.getReportText())
                    .story(tempStory)
                    .type(tempReportType)
                    .user(tempUser)
                    .build());
        }
         return arrayList;
    }

    public ReportDta getReportDta(Optional<Report> byId) {
        StoryDta tempStory =storyMapper.giveDtaStory(byId.get().getStory());
        ReportTypeDta tempReportType = giveDtaReportType(byId.get().getType());
        UserDta tempUser = Usermapper.giveDta(byId.get().getUser());

        return ReportDta.builder()
                .reportid(byId.get().getReportid())
                .reportText(byId.get().getReportText())
                .story(tempStory)
                .type(tempReportType)
                .user(tempUser)
                .build();
    }

    public ReportTypeDta giveDtaReportType(ReportType type) {
        return ReportTypeDta.builder()
                .reportTypeId(type.getReportTypeId())
                .type(type.getType())
                .build();
    }

    public List<ReportTypeDta> giveDtaReportType(List<ReportType> types) {
        List<ReportTypeDta> list = new ArrayList<>();
        for(ReportType type: types) {
            list.add( ReportTypeDta.builder()
                    .reportTypeId(type.getReportTypeId())
                    .type(type.getType())
                    .build()
            );
        }
        return list;
    }

    public ReportType giveEntityReportType(ReportTypeDta type) {
        return ReportType.builder()
                .reportTypeId(type.getReportTypeId())
                .type(type.getType())
                .build();
    }

}
