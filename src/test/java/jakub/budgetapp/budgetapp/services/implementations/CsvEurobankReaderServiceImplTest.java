package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link CsvEurobankReaderServiceImpl}
 */
class CsvEurobankReaderServiceImplTest {

    private CsvEurobankReaderServiceImpl readerService;
    private List<FinancialOperation> incomes;
    private List<FinancialOperation> expenses;

    @BeforeEach
    void setUp() {
        readerService = new CsvEurobankReaderServiceImpl();
        incomes = new LinkedList<>();
        expenses = new LinkedList<>();
    }

    @AfterEach
    void tearDown() {
        incomes.clear();
        expenses.clear();
    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_havingOperationWithMinusValue_shouldSaveItIntoExpenses() {
        String[] values = {"30-05-2019", "03-06-2019", "\"Płatność kartą Kwota zakupu: 20,00PLN  \"", "-20,00 PLN",
                ";6233,74 PLN"};

        readerService.informationExtracting(incomes, expenses, values);

        FinancialOperation operation = expenses.get(0);

        assertAll(
                () -> assertEquals(0, incomes.size()),
                () -> assertEquals(1, expenses.size()),
                () -> assertEquals("-20,00 PLN", operation.getCosts()),
                () -> assertEquals("Płatność kartą Kwota zakupu: 20,00PLN ", operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("30-05-2019", operation.getDate())
        );

    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_havingOperationWithPlusValue_shouldSaveItIntoIncomes() {
        String[] values = {"30-05-2019", "03-06-2019", "\"Płatność kartą Kwota zakupu: 20,00PLN  \"", "20,00 PLN",
                ";6233,74 PLN"};

        readerService.informationExtracting(incomes, expenses, values);

        FinancialOperation operation = incomes.get(0);

        assertAll(
                () -> assertEquals(1, incomes.size()),
                () -> assertEquals(0, expenses.size()),
                () -> assertEquals("20,00 PLN", operation.getCosts()),
                () -> assertEquals("Płatność kartą Kwota zakupu: 20,00PLN ", operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("30-05-2019", operation.getDate())
        );

    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[])}
     */
    @Test
    void informationExtracting_whenValuesAreEmpty_ThrowException() {
        String[] values = {"", "cba", "abc"};
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> readerService.informationExtracting(incomes, expenses, values));
    }

}