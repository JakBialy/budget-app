package jakub.budgetapp.budgetapp.dtos;

import jakub.budgetapp.budgetapp.services.enums.Currency;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FinancialOperationDto {
    private Long id;

    private String costs;

    private String description;

    private String category;

    private Currency currency;

    private String date;
}
