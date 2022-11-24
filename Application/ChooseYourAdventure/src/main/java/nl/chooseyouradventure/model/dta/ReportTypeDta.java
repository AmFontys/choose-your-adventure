package nl.chooseyouradventure.model.dta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTypeDta {
    private int reportTypeId;
    private String type;
}
