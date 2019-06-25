package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.services.enums.Bank;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CsvReaderHelper {

    Optional<Bank> checkBankType (MultipartFile csvFile);
}
