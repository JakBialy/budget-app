package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CsvMbankReaderServiceImpl
 * {@link CsvMbankReaderServiceImpl }
 */
class CsvMbankReaderServiceImplTest {

    private CsvMbankReaderServiceImpl readerService;
    private List<FinancialOperationDto> incomes;
    private List<FinancialOperationDto> expenses;

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
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_havingOperationWithMinusValue_shouldSaveItIntoExpenses() {
        String[] values = {"2018-02-01", "2018-02-01", "PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY", "\"GARAŻ\"",
                "\"PIOTR  LIPSKI                      UL. VAN GOGHA 9  M.43  03-188 WARSZAWA\"",
                "'60102011850000450202403772'", "-400,00", "416,31"};

        readerService.informationExtracting(incomes, expenses, values, Currency.PLN);

        FinancialOperationDto operation = expenses.get(0);

        assertAll(
                () -> assertEquals(0, incomes.size()),
                () -> assertEquals(1, expenses.size()),
                () -> assertEquals("-400.00", operation.getCosts()),
                () -> assertEquals("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY,GARAŻ,PIOTR LIPSKI UL. VAN GOGHA 9 M.43 03-188 WARSZAWA",
                        operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2018-02-01", operation.getDate())
        );

    }

    /**
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_havingOperationWithPlusValue_shouldSaveItIntoIncomes() {
        String[] values = {"2018-02-01", "2018-02-01", "PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY", "\"GARAŻ\"",
                "\"PIOTR  LIPSKI                      UL. VAN GOGHA 9  M.43  03-188 WARSZAWA\"",
                "'60102011850000450202403772'", "400,00", "416,31"};

        readerService.informationExtracting(incomes, expenses, values, Currency.PLN);

        FinancialOperationDto operation = incomes.get(0);

        assertAll(
                () -> assertEquals(0, expenses.size()),
                () -> assertEquals(1, incomes.size()),
                () -> assertEquals("400.00", operation.getCosts()),
                () -> assertEquals("PRZELEW ZEWNĘTRZNY PRZYCHODZĄCY,GARAŻ,PIOTR LIPSKI UL. VAN GOGHA 9 M.43 03-188 WARSZAWA",
                        operation.getDescription()),
                () -> assertNull(operation.getCategory()),
                () -> assertEquals("2018-02-01", operation.getDate())
        );

    }

    /**
     * {@link CsvMbankReaderServiceImpl#informationExtracting(List, List, String[], Currency)}
     */
    @Test
    void informationExtracting_whenValuesAreEmpty_ThrowException() {
        String[] values = {"", "abc"};
        assertThrows(ArrayIndexOutOfBoundsException.class,() -> readerService.informationExtracting(incomes, expenses, values, Currency.PLN));
    }

    /**
     * {@link CsvMbankReaderServiceImpl#checkCurrency(String[])}
     */
    @ParameterizedTest(name = "checkCurrencySource having value to check as a \"{0}\" should return \"{1}\"")
    @MethodSource("checkCurrencySource")
    void checkCurrency(String currencyValue, Currency expectedCurrency){
        String[] check = new String[] {currencyValue};
        assertEquals(expectedCurrency, readerService.checkCurrency(check));
    }

    private static Stream<Arguments> checkCurrencySource(){
        return Stream.of(
                Arguments.of("PLN", Currency.PLN),
                Arguments.of("#Numer rachunku", Currency.UNKNOWN)
        );
    }

}