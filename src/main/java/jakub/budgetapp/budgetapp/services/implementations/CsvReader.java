package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public abstract class CsvReader implements CsvReaderService {

    private static final String COMMA_SEPARATOR = ";";
    private Currency currency;

    int dateInput;
    int descriptionInput;
    int costs;
    String startingPoint;
    String charsetName;

    /**
     *
     * @param file csv file containing information about operations
     */
    @Override
    public List<List<FinancialOperationDto>> readCsvFile(MultipartFile file) {
        List<FinancialOperationDto> incomes = new LinkedList<>();
        List<FinancialOperationDto> expenses = new LinkedList<>();

        informationRecording(file, incomes, expenses);

        List<List<FinancialOperationDto>> operations = new LinkedList<>();
        operations.add(incomes);
        operations.add(expenses);

        return operations;
    }

    /**
     * Checks what currency account is state in CSV file
     * @param values file lines
     * @return enum type of currency
     */
    abstract Currency checkCurrency(String[] values);

    private void informationRecording(MultipartFile file, List<FinancialOperationDto> incomes,
                                      List<FinancialOperationDto> expenses) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), charsetName)) ) {

            boolean recording = false;
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_SEPARATOR);

                if (currency == null){
                    currency = checkCurrency(values);
                }

                if (recording && values.length <= 1){
                    recording = false;
                }

                if (recording){
                    informationExtracting(incomes, expenses, values, currency);
                    continue;
                }

                if (line.equals(startingPoint)) {
                    recording = true;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Records information for both positive and negative operation
     * and save them into two lists (incomes, expenses)
     * @param incomes list of incomes
     * @param expenses list of expenses
     * @param values values from the csv file line
     */
    abstract void informationExtracting(List<FinancialOperationDto> incomes, List<FinancialOperationDto> expenses,
                                        String[] values, Currency currency);
}
