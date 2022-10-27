package nl.ChooseYourAdventure.model;

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
@Entity
public class Storybody {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="storyid")
    private Story story;

    @ManyToOne
    @JoinColumn(name="storybody_typeid")
    private StoryBodyType type;

    @NotNull
    @Length(min=7,max=150)
    private String body_title;

    @NotNull
    private String text;




}

