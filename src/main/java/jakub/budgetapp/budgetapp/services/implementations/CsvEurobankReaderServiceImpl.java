package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Specific reader of .csv files with expenses for polish bank - eurobank
 */
@Service
public class CsvEurobankReaderServiceImpl extends CsvReader {
    // this guys below should be put into some kind of properties!
    // duplicates!
    private static final char MINUS = '-';
    private static final String SPACE = " ";
    private static final String CURRENCY_KEYWORD = "Waluta:";

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
    Currency checkCurrency(String[] values) {
        Optional<Currency> currency = Optional.empty();
        if (values[0].startsWith(CURRENCY_KEYWORD)){
            try {
                currency = Optional.of(Currency.valueOf(values[0].substring(CURRENCY_KEYWORD.length())));
            } catch (IllegalArgumentException ex){
                System.out.println("There's no such value and currency will be interpreted as unknown");
                // should be put to proper logger
            }
            return currency.orElse(Currency.UNKNOWN);
        }
        return currency.orElse(null);
    }

    @Override
    void informationExtracting(List<FinancialOperationDto> incomes, List<FinancialOperationDto> expenses,
                               String[] values, Currency currency) {

        FinancialOperationDto financialOperationDto = FinancialOperationDto
                .builder()
                .date(eurobankDataStandardization(values[dateInput]))
                .description(eurobankStringBeautifier(values[descriptionInput]))
                .costs(values[costs].replace(",",".").replace(" PLN", ""))
                .currency(currency)
                .build();

        if (values[costs].charAt(0) == MINUS){
            expenses.add(financialOperationDto);
        } else {
            incomes.add(financialOperationDto);
        }

    }

    private String eurobankStringBeautifier(String description) {

        StringBuilder beautifulValue = new StringBuilder();
        beautifulValue.append(description);
        beautifulValue.deleteCharAt(0).deleteCharAt(beautifulValue.length() - 1);

        return beautifulValue.toString().trim().replaceAll(MULTI_IRREGULAR_SPACE_REGEX, SPACE);
    }

    /**
     * Reverts data format from DD-MM-YYYY to YYYY-MM-DD
     * @param date String with date in format of DD-MM-YYYY
     * @return String in format of YYYY-MM-DD
     */
    private String eurobankDataStandardization(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter).toString();
    }

}
