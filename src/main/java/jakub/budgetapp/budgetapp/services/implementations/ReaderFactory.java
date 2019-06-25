package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Bank;
import org.springframework.stereotype.Service;

@Service
public class ReaderFactory {

    /**
     * Method is returning recognized type of Bank as a Bank enum
     * @param bank enum of recognized bank
     * @return reader service for a given bank
     */
    CsvReaderService getCsvReaderService(Bank bank){

        switch (bank){
            case EUROBANK:
                return new CsvEurobankReaderServiceImpl();

            case MBANK:
                return new CsvMbankReaderServiceImpl();

            default:
                return null;
        }

    }

}
