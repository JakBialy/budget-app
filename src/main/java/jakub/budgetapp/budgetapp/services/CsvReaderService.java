package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvReaderService {

    List<List<FinancialOperation>> readCsvFile(MultipartFile file);
}
