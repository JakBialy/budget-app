package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.services.CsvReaderHelper;
import jakub.budgetapp.budgetapp.services.enums.Banks;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class CsvReadHelperImpl implements CsvReaderHelper {
    // this guys below should be put into some kind of properties!
    // duplicates!
    private static final String COMMA_SEPARATOR = ";";
    private static final String CHARSET_NAME = "Windows-1250";
    private static final String KEY_WORD_MBANK = "mBank S.A. Bankowość Detaliczna";
    private static final String KEY_WORD_EUROBANK = "Numer konta:39 1470 0002 2007 2197 1000 0001";

    @Override
    public Optional<Banks> checkBankType (MultipartFile csvFile) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), CHARSET_NAME)) ) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_SEPARATOR);

                if (values[0].equals(KEY_WORD_MBANK)){
                    return Optional.of(Banks.MBANK);
                } else if (values[0].equals(KEY_WORD_EUROBANK)){
                    return Optional.of(Banks.EUROBANK);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }
}
