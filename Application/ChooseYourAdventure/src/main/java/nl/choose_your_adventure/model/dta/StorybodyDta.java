package nl.choose_your_adventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorybodyDta {
    private int id;
    private StoryDta story;
    private StoryBodyTypeDta type;
    private String body_title;
    private String text;




}

