package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
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
    BankFactory bankFactory;

    private final
    ReaderFactory readerFactory;

    public MainCsvReaderServiceImpl(BankFactory bankFactory, ReaderFactory readerFactory) {
        this.bankFactory = bankFactory;
        this.readerFactory = readerFactory;
    }

    @Override
    public List<List<FinancialOperationDto>> mainCSVReading (MultipartFile csvFile){

        Optional<Bank> bank = bankFactory.getBank(csvFile);
        List<List<FinancialOperationDto>> operations = new ArrayList<>();

        if (bank.isPresent()){
            CsvReaderService csvReaderService = readerFactory.getCsvReaderService(bank.get());
            operations = csvReaderService.readCsvFile(csvFile);
        }

        return operations;
    }
}
