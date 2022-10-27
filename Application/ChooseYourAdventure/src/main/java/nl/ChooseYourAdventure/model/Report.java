package nl.ChooseYourAdventure.model;

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
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportid;

    @NotNull
    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name="storyid")
    private Story story;

    @NotNull
    @ManyToOne
    @JoinColumn(name="report_typeid")
    private ReportType type;

    @NotNull
    private String report;

}
