package nl.ChooseYourAdventure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    @NotNull
    @Length(min=2,max=100)
    private String username;

    @NotNull
    @Length(min=7,max=150)
    private String email;

    @NotNull
    @Length(min=7,max=150)
    private String password;

    @NotNull
    private String keyword;

    @NotNull
    private Boolean ismod;

}
