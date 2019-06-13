package jakub.budgetapp.budgetapp.services.implementations;


import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvMbankReaderServiceImpl extends CsvReader{
    private static final char MINUS = '-';
    private static final int TITLE = 3;
    private static final int INCOMER_OUTCOMER = 4;


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
                .description(values[descriptionInput] + values[TITLE] + values[INCOMER_OUTCOMER])
                .costs(values[costs])
                .build();


        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperation);
        } else {
            incomes.add(financialOperation);
        }
    }
}
