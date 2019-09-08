package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.services.enums.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for {@link FinancialOperationRepository}
 */
@DataJpaTest
class FinancialOperationRepositoryTest {
    private static final String TEST_DESCRIPTION = "TEST-DESCRIPTION";
    private static final String TEST_CATEGORY = "TEST-CATEGORY";
    private static final LocalDate TEST_TIME = LocalDate.now().minusDays(5);
    private static final LocalDate TEST_TIME_2 = LocalDate.now().minusDays(1);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FinancialOperationRepository financialOperationRepository;

    private User user;

    private FinancialOperation entityOne;

    private FinancialOperation entityTwo;

    @BeforeEach
    void setUp(){
        user = User
                .builder()
                .password("abc123")
                .username("test-user")
                .build();

        user = entityManager.persist(user);

        entityOne = FinancialOperation
                .builder()
                .costs(BigDecimal.TEN)
                .description(TEST_DESCRIPTION)
                .category(TEST_CATEGORY)
                .currency(Currency.PLN)
                .date(TEST_TIME)
                .user(user)
                .build();

        entityTwo = FinancialOperation
                .builder()
                .costs(BigDecimal.ONE)
                .description(TEST_DESCRIPTION)
                .category(TEST_CATEGORY)
                .currency(Currency.UNKNOWN)
                .date(TEST_TIME_2)
                .user(user)
                .build();

        entityManager.persist(entityOne);
        entityManager.persist(entityTwo);
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    /**
     * {@link FinancialOperationRepository#findAllByUserId(Long)}
     */
    @Test
    void findAllByUserId_thereIsSetOfOperationsSaved_shouldReturnThem() {
        List<FinancialOperation> financialOperationList = financialOperationRepository.findAllByUserId(user.getId());

        assertAll(
                () -> assertEquals(2, financialOperationList.size()),
                () -> assertEquals(BigDecimal.TEN, financialOperationList.get(0).getCosts()),
                () -> assertEquals(BigDecimal.ONE, financialOperationList.get(1).getCosts())
        );
    }

    /**
     * {@link FinancialOperationRepository#removeAllByUserId(Long)}
     */
    @Test
    void removeAllByUserId_thereIsSetOfOperationsSaved_shouldDeleteAllOfThem() {
        financialOperationRepository.removeAllByUserId(user.getId());
        assertEquals(0, financialOperationRepository.findAll().size());
    }

    /**
     * {@link FinancialOperationRepository#removeGroupOfOperationsByIdsAndUser(List, User)}
     */
    @Test
    void removeAllByIdAndUserId_thereIsSetOfOperationsSaved_shouldDeleteSomeOfThem() {
        financialOperationRepository.removeGroupOfOperationsByIdsAndUser(Arrays.asList(entityOne.getId(), entityTwo.getId()), user);
        assertEquals(0, financialOperationRepository.findAll().size());
    }

    /**
     * {@link FinancialOperationRepository#findAllByUserAndDateBetween(User, LocalDate, LocalDate)}
     */
    @Test
    void findAllByLocalBetween_lookingForOperationsBetweenGivenDates_shouldReturnProperOne() {
        List<FinancialOperation> financialOperations = financialOperationRepository
                .findAllByUserAndDateBetween(user, LocalDate.now().minusDays(2), LocalDate.now());

        assertAll(
                () -> assertEquals(1, financialOperations.size()),
                () -> assertEquals(BigDecimal.ONE, financialOperations.get(0).getCosts())
        );
    }

}
