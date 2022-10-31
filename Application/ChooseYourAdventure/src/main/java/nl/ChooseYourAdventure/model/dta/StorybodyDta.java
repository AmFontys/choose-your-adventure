package nl.ChooseYourAdventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

