package jakub.budgetapp.budgetapp.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FinancialOperation {

    private String costs;

    private String description;

    private String category;

    private String date;
}
