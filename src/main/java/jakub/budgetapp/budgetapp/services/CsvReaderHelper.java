package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.services.enums.Banks;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CsvReaderHelper {

    Optional<Banks> checkBankType (MultipartFile csvFile);
}
