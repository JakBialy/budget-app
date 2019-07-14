package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CsvMbankReaderServiceImpl
 * {@link CsvMbankReaderServiceImpl }
 */
class CsvMbankReaderServiceImplTest {

    private CsvMbankReaderServiceImpl readerService;
    private List<FinancialOperation> incomes;
    private List<FinancialOperation> expenses;

    @BeforeEach
    void setUp() {
        readerService = new CsvMbankReaderServiceImpl();
        incomes = new LinkedList<>();
        expenses = new LinkedList<>();
    }

    @AfterEach
    void tearDown() {
        incomes.clear();
        expenses.clear();
    }

    /**
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_havingOperationWithMinusValue_shouldSaveItIntoExpenses() {
        String[] values = {"2018-02-01", "2018-02-01", "PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY", "\"GARAŻ\"",
                "\"PIOTR  LIPSKI                      UL. VAN GOGHA 9  M.43  03-188 WARSZAWA\"",
                "'60102011850000450202403772'", "-400,00", "416,31"};

        readerService.informationExtracting(incomes, expenses, values);

        FinancialOperation operation = expenses.get(0);

        assertAll(
                () -> assertEquals(0, incomes.size()),
                () -> assertEquals(1, expenses.size()),
                () -> assertEquals("-400,00", operation.getCosts()),
                () -> assertEquals("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY,GARAŻ,PIOTR LIPSKI UL. VAN GOGHA 9 M.43 03-188 WARSZAWA",
                        operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2018-02-01", operation.getDate())
        );

    }

    /**
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_havingOperationWithPlusValue_shouldSaveItIntoIncomes() {
        String[] values = {"2018-02-01", "2018-02-01", "PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY", "\"GARAŻ\"",
                "\"PIOTR  LIPSKI                      UL. VAN GOGHA 9  M.43  03-188 WARSZAWA\"",
                "'60102011850000450202403772'", "400,00", "416,31"};

        readerService.informationExtracting(incomes, expenses, values);

        FinancialOperation operation = incomes.get(0);

        assertAll(
                () -> assertEquals(0, expenses.size()),
                () -> assertEquals(1, incomes.size()),
                () -> assertEquals("400,00", operation.getCosts()),
                () -> assertEquals("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY,GARAŻ,PIOTR LIPSKI UL. VAN GOGHA 9 M.43 03-188 WARSZAWA",
                        operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2018-02-01", operation.getDate())
        );

    }

    /**
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_whenValuesAreEmpty_ThrowException() {
        String[] values = {"", "abc"};
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> readerService.informationExtracting(incomes, expenses, values));
    }
}