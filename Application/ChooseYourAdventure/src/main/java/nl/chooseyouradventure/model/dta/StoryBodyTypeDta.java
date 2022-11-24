package nl.chooseyouradventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoryBodyTypeDta {
    private int storybody_typeId;
    private String typename;
}
