package jakub.budgetapp.budgetapp.services.implementations;


import jakub.budgetapp.budgetapp.services.CsvReaderService;
import jakub.budgetapp.budgetapp.services.enums.Bank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class ReaderFactoryTest {

    private ReaderFactory readerFactory = new ReaderFactory();

    /**
     * @link {@link ReaderFactory#getCsvReaderService(Bank)}
     */
    @Test
    public void testGetCsvReaderService_havingMbankEnum_shouldReturnCorrectService() {
        CsvReaderService service = readerFactory.getCsvReaderService(Bank.MBANK);
        assertTrue(service instanceof CsvMbankReaderServiceImpl);
    }

    /**
     * @link {@link ReaderFactory#getCsvReaderService(Bank)}
     */
    @Test
    public void testGetCsvReaderService_havingEurobankEnum_shouldReturnCorrectService() {
        CsvReaderService service = readerFactory.getCsvReaderService(Bank.EUROBANK);
        assertTrue(service instanceof CsvEurobankReaderServiceImpl);
    }

}