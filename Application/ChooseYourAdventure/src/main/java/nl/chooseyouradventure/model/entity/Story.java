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
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storyid;

    @NotNull
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @NotNull

    private String title;

}
