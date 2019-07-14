package jakub.budgetapp.budgetapp.services.implementations;


import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Bank;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

class ReaderFactoryTest {

    private ReaderFactory readerFactory = new ReaderFactory();

    /**
     * @link {@link ReaderFactory#getCsvReaderService(Bank)}
     */
    @Test
    void testGetCsvReaderService_havingMbankEnum_shouldReturnCorrectService() {
        CsvReaderService service = readerFactory.getCsvReaderService(Bank.MBANK);
        assertTrue(service instanceof CsvMbankReaderServiceImpl);
    }

    /**
     * @link {@link ReaderFactory#getCsvReaderService(Bank)}
     */
    @Test
    void testGetCsvReaderService_havingEurobankEnum_shouldReturnCorrectService() {
        CsvReaderService service = readerFactory.getCsvReaderService(Bank.EUROBANK);
        assertTrue(service instanceof CsvEurobankReaderServiceImpl);
    }

}