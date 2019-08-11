package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvReaderService {

    List<List<FinancialOperationDto>> readCsvFile(MultipartFile file);
}
