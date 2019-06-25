package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Specific reader of .csv files with expenses for polish bank - eurobank
 */
@Service
public class CsvEurobankReaderServiceImpl extends CsvReader {
    // this guys below should be put into some kind of properties!
    // duplicates!
    private static final char MINUS = '-';
    private static final String SPACE = " ";

    /**
     * worth noting - this space is different than regular one
     */
    private static final String MULTI_IRREGULAR_SPACE_REGEX = " +";


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
                .description(eurobankStringBeautifier(values[descriptionInput]))
                .costs(values[costs])
                .build();

        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperation);
        } else {
            incomes.add(financialOperation);
        }

    }

    private String eurobankStringBeautifier(String value) {

        StringBuilder beautifulValue = new StringBuilder();
        beautifulValue.append(value);
        beautifulValue.deleteCharAt(0).deleteCharAt(beautifulValue.length() - 1);

        return beautifulValue.toString().trim().replaceAll(MULTI_IRREGULAR_SPACE_REGEX, SPACE);
    }

}
