package nl.ChooseYourAdventure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StoryBodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storybody_typeid;

    @NotNull
    private String typename;

}
