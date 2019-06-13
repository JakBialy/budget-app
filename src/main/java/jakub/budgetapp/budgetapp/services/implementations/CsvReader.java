package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import jakub.budgetapp.budgetapp.services.CsvReaderService;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class CsvReader implements CsvReaderService {

    private static final String COMMA_SEPARATOR = ";";

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
    public List<List<FinancialOperation>> readCsvFile(MultipartFile file) {
        List<FinancialOperation> incomes = new ArrayList<>();
        List<FinancialOperation> expenses = new ArrayList<>();

        informationRecording(file, incomes, expenses);

        List<List<FinancialOperation>> operations = new ArrayList<>();
        operations.add(incomes);
        operations.add(expenses);

        return operations;
    }

    private void informationRecording(MultipartFile file, List<FinancialOperation> incomes, List<FinancialOperation> expenses) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), charsetName)) ) {

            boolean recording = false;
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_SEPARATOR);

                if (recording && values.length <= 1){
                    recording = false;
                }

                if (recording){
                    informationExtracting(incomes, expenses, values);
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
    abstract void informationExtracting(List<FinancialOperation> incomes, List<FinancialOperation> expenses, String[] values);
}
