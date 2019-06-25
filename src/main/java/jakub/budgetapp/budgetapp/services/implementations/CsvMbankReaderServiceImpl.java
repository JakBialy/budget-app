package jakub.budgetapp.budgetapp.services.implementations;


import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Specific reader of .csv files with expenses for polish bank - mbank
 */
@Service
public class CsvMbankReaderServiceImpl extends CsvReader{
    // this guys below should be put into some kind of properties!
    // duplicates!
    private static final char MINUS = '-';
    private static final int TITLE = 3;
    private static final int INCOMER_OUTCOMER = 4;
    private static final String SPACE = " ";
    private static final String MULTI_SPACE_REGEX = " +";
    private static final char SEPARATOR = ',';


    public CsvMbankReaderServiceImpl() {
        super.dateInput = 0;
        super.descriptionInput = 2;
        super.costs = 6;
        super.startingPoint = "#Data operacji;#Data księgowania;#Opis operacji;#Tytuł;#Nadawca/Odbiorca;#Numer konta;#Kwota;#Saldo po operacji;";
        super.charsetName = "Windows-1250";
    }

    @Override
    void informationExtracting(List<FinancialOperation> incomes, List<FinancialOperation> expenses, String[] values) {

        FinancialOperation financialOperation = FinancialOperation
                .builder()
                .date(values[dateInput])
                .description(values[descriptionInput] + mbankStringBeautifier(values[TITLE]) + mbankStringBeautifier(values[INCOMER_OUTCOMER]))
                .costs(values[costs])
                .build();

        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperation);
        } else {
            incomes.add(financialOperation);
        }
    }

    private String mbankStringBeautifier(String value) {

        StringBuilder beautifulValue = new StringBuilder();

        beautifulValue.append(value);
        beautifulValue.setCharAt(0, SEPARATOR);
        beautifulValue.deleteCharAt(beautifulValue.length() - 1);

        return beautifulValue.toString().trim().replaceAll(MULTI_SPACE_REGEX, SPACE);
    }
}
