package jakub.budgetapp.budgetapp.services.implementations;


import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    private static final String MULTI_IRREGULAR_SPACE_REGEX = " +";
    private static final char SEPARATOR = ',';
    private static final String STRING_AFTER_CURRENCY_CHECK = "#Numer rachunku";

    public CsvMbankReaderServiceImpl() {
        super.dateInput = 0;
        super.descriptionInput = 2;
        super.costs = 6;
        super.startingPoint = "#Data operacji;#Data księgowania;#Opis operacji;#Tytuł;#Nadawca/Odbiorca;#Numer konta;#Kwota;#Saldo po operacji;";
        super.charsetName = "Windows-1250";
    }

    @Override
    Currency checkCurrency(String[] values) {
        if (checkIfLineIsCurrencyValue(values[0])){
            Optional<Currency> currency = Optional.empty();
            try {
                currency = Optional.of(Currency.valueOf(values[0]));
            } catch (IllegalArgumentException ex){
                System.out.println("There's no such value and currency will be interpreted as unknown");
                // should be moved to a proper logger
            }

            return currency.orElse(Currency.UNKNOWN);
        } else if(values[0].equals(STRING_AFTER_CURRENCY_CHECK)) {
            return Currency.UNKNOWN;
        }

        return null;
    }

    private boolean checkIfLineIsCurrencyValue(String value) {
        for (Currency currency: Currency.values()) {
            if(currency.name().equals(value)){
                return true;
            }
        }

        return false;
    }

    @Override
    void informationExtracting(List<FinancialOperationDto> incomes, List<FinancialOperationDto> expenses
            ,String[] values, Currency currency) {

        FinancialOperationDto financialOperationDto = FinancialOperationDto
                .builder()
                .date(values[dateInput])
                .description(values[descriptionInput] + mbankStringBeautifier(values[TITLE]) + mbankStringBeautifier(values[INCOMER_OUTCOMER]))
                .costs(values[costs].replace(",", "."))
                .currency(currency)
                .build();

        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperationDto);
        } else {
            incomes.add(financialOperationDto);
        }
    }

    private String mbankStringBeautifier(String value) {

        StringBuilder beautifulValue = new StringBuilder();

        beautifulValue.append(value);
        beautifulValue.setCharAt(0, SEPARATOR);
        beautifulValue.deleteCharAt(beautifulValue.length() - 1);

        return beautifulValue.toString().trim().replaceAll(MULTI_IRREGULAR_SPACE_REGEX, SPACE);
    }


}
