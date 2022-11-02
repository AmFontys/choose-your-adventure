package nl.choose_your_adventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDta {

    private int reportid;
    private UserDta user;
    private StoryDta story;
    private ReportTypeDta type;
    private String report;

}
