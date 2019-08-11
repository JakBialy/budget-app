package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link CsvEurobankReaderServiceImpl}
 */
class CsvEurobankReaderServiceImplTest {

    private CsvEurobankReaderServiceImpl readerService;
    private List<FinancialOperationDto> incomes;
    private List<FinancialOperationDto> expenses;

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
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_havingOperationWithMinusValue_shouldSaveItIntoExpenses() {
        String[] values = {"30-05-2019", "03-06-2019", "\"Płatność kartą Kwota zakupu: 20,00PLN  \"", "-20,00 PLN",
                ";6233,74 PLN"};

        readerService.informationExtracting(incomes, expenses, values, Currency.PLN);

        FinancialOperationDto operation = expenses.get(0);

        assertAll(
                () -> assertEquals(0, incomes.size()),
                () -> assertEquals(1, expenses.size()),
                () -> assertEquals("-20.00", operation.getCosts()),
                () -> assertEquals("Płatność kartą Kwota zakupu: 20,00PLN ", operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2019-05-30", operation.getDate())
        );

    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_havingOperationWithPlusValue_shouldSaveItIntoIncomes() {
        String[] values = {"30-05-2019", "03-06-2019", "\"Płatność kartą Kwota zakupu: 20,00PLN  \"", "20,00 PLN",
                ";6233,74 PLN"};

        readerService.informationExtracting(incomes, expenses, values, Currency.PLN);

        FinancialOperationDto operation = incomes.get(0);

        assertAll(
                () -> assertEquals(1, incomes.size()),
                () -> assertEquals(0, expenses.size()),
                () -> assertEquals("20.00", operation.getCosts()),
                () -> assertEquals("Płatność kartą Kwota zakupu: 20,00PLN ", operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2019-05-30", operation.getDate())
        );

    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_whenValuesAreEmpty_ThrowDateTimeParseException() {
        String[] values = {"03-06-2019", "cba", "abc"};
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> readerService.informationExtracting(incomes, expenses, values, Currency.PLN));
    }

    /**
     * {@link CsvEurobankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_whenValuesAreEmpty_ThrowException() {
        String[] values = {"", "cba", "abc"};
        assertThrows(DateTimeParseException.class,
                () -> readerService.informationExtracting(incomes, expenses, values, Currency.PLN));
    }

    /**
     * {@link CsvEurobankReaderServiceImpl#checkCurrency(String[])}
     */
    @ParameterizedTest(name = "checkCurrencySource having value to check as a \"{0}\" should return \"{1}\"")
    @MethodSource("checkCurrencySource")
    void checkCurrency(String keyWordAndValue, Currency expectedCurrency) {
        String[] toCheck = new String[] {keyWordAndValue};
        assertEquals(expectedCurrency, readerService.checkCurrency(toCheck));
    }

    private static Stream<Arguments> checkCurrencySource(){
        return Stream.of(
                Arguments.of("Waluta:PLN", Currency.PLN),
                Arguments.of("Waluta:XYZ", Currency.UNKNOWN)
        );
    }
    
}