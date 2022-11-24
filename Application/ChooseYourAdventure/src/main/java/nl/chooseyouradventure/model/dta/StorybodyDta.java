package nl.chooseyouradventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorybodyDta {
    private int id;
    private StoryDta story;
    private StoryBodyTypeDta type;
    private String bodyTitle;
    @Lob
    private String text;




}

