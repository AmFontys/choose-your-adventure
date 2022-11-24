package nl.chooseyouradventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryDta {
    private int storyid;
    private UserDta user;
    private String title;

}
