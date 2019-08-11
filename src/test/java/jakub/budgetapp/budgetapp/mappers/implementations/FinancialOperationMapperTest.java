package jakub.budgetapp.budgetapp.mappers.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.entites.FinancialOperation;

import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link FinancialOperationMapper}
 */
class FinancialOperationMapperTest {

    private static final String TEST_DESCRIPTION = "TEST-DESCRIPTION";
    private static final String TEST_CATEGORY = "TEST-CATEGORY";
    private static final LocalDate TEST_TIME = LocalDate.now().minusDays(5);

    private FinancialOperationMapper financialOperationMapper;


    @BeforeEach
    void setUp() {
        financialOperationMapper = new FinancialOperationMapper();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toDto() {
        FinancialOperation entity = FinancialOperation
                .builder()
                .id(1L)
                .costs(BigDecimal.TEN)
                .description(TEST_DESCRIPTION)
                .category(TEST_CATEGORY)
                .currency(Currency.PLN)
                .localDate(TEST_TIME)
                .build();

        FinancialOperationDto dto = financialOperationMapper.toDto(entity);

        assertAll(
                () -> assertEquals(BigDecimal.TEN.toString(), dto.getCosts()),
                () -> assertEquals(TEST_DESCRIPTION, dto.getDescription()),
                () -> assertEquals(TEST_CATEGORY, dto.getCategory()),
                () -> assertEquals(Currency.PLN, dto.getCurrency()),
                () -> assertEquals(TEST_TIME.toString(), dto.getDate()),
                () -> assertEquals((Long)1L, dto.getId())
        );
    }

    @Test
    void toEntity() {
        FinancialOperationDto dto = FinancialOperationDto
                .builder()
                .id(1L)
                .costs(BigDecimal.TEN.toString())
                .description(TEST_DESCRIPTION)
                .category(TEST_CATEGORY)
                .currency(Currency.PLN)
                .date(TEST_TIME.toString())
                .build();

        FinancialOperation entity = financialOperationMapper.toEntity(dto);

        assertAll(
                () -> assertEquals(BigDecimal.TEN, entity.getCosts()),
                () -> assertEquals(TEST_DESCRIPTION, entity.getDescription()),
                () -> assertEquals(TEST_CATEGORY, entity.getCategory()),
                () -> assertEquals(Currency.PLN, entity.getCurrency()),
                () -> assertEquals(TEST_TIME, entity.getLocalDate()),
                () -> assertEquals((Long)1L, entity.getId())
        );
    }
}