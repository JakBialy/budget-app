package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.MainCsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Bank;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MainCsvReaderServiceImpl implements MainCsvReaderService {

    private final
    CsvReadHelperImpl csvReadHelperImpl;

    private final
    ReaderFactory readerFactory;

    public MainCsvReaderServiceImpl(CsvReadHelperImpl csvReadHelperImpl, ReaderFactory readerFactory) {
        this.csvReadHelperImpl = csvReadHelperImpl;
        this.readerFactory = readerFactory;
    }

    @Override
    public List<List<FinancialOperation>> mainCSVReading (MultipartFile csvFile){

        Optional<Bank> banks = csvReadHelperImpl.checkBankType(csvFile);
        List<List<FinancialOperation>> operations = new ArrayList<>();

        if(banks.isPresent()){
            CsvReaderService csvReaderService = readerFactory.getCsvReaderService(banks.get());
            operations = csvReaderService.readCsvFile(csvFile);
        }

        return operations;
    }
}
