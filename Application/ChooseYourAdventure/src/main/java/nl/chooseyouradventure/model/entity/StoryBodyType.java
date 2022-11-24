package nl.chooseyouradventure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StoryBodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storyBodyTypeId;

    @NotNull
    private String typename;

}
