package nl.ChooseYourAdventure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import javax.validation.constraints.NotNull;
import java.util.Dictionary;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryBody {
    @NotNull
    private int id;
    @NotNull
    private String storyId;
    @NotNull
    private String bodyTitle;
    @NotNull
    private String text;

    private Dictionary<String, StoryBody> options;


}
