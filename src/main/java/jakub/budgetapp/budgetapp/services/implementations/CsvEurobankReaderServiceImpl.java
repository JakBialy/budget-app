package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

@Service
public class CsvEurobankReaderServiceImpl extends CsvReader {
    private static final char MINUS = '-';


    public CsvEurobankReaderServiceImpl() {
        super.dateInput = 0;
        super.descriptionInput = 2;
        super.costs = 3;
        super.startingPoint = "Data operacji; Data księgowania; Opis operacji; Kwota; Saldo po operacji; Nadawca nrb;";
        super.charsetName = "Windows-1250";
    }

    @Override
    void informationExtracting(List<FinancialOperation> incomes, List<FinancialOperation> expenses, String[] values) {

        FinancialOperation financialOperation = FinancialOperation
                .builder()
                .date(values[dateInput])
                .description(values[descriptionInput])
                .costs(values[costs])
                .build();

        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperation);
        } else {
            incomes.add(financialOperation);
        }

    }

}
