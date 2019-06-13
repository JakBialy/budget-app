package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Banks;
import org.springframework.stereotype.Service;

@Service
public class ReaderFactory {

    CsvReaderService getCsvReaderService(Banks banks){

        switch (banks){
            case EUROBANK:
                return new CsvEurobankReaderServiceImpl();

            case MBANK:
                return new CsvMbankReaderServiceImpl();
        }

        return null;
    }

}
