package nl.chooseyouradventure.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

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
    private String bodyTitle;

    @Lob
    @Basic(fetch = LAZY)
    @NotNull
    @Column(name="text")
    @Type(type="text")
    private String text;




}

