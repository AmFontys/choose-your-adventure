package nl.chooseyouradventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDta {
    private int userid;
    private String username;
    private String email;
    private String password;
    private String keyword;
    private Boolean ismod;
    private String accesToken;
}
