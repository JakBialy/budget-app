package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.mappers.implementations.FinancialOperationMapper;
import jakub.budgetapp.budgetapp.repositories.FinancialOperationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link FinancialOperationServiceImpl}
 */
@ExtendWith(SpringExtension.class)
class FinancialOperationServiceImplTest {

    private FinancialOperationServiceImpl financialOperationService;

    @Mock
    FinancialOperationRepository financialOperationRepository;

    @Mock
    Authentication authentication;

    @Mock
    SecurityContext securityContext;

    private FinancialOperationMapper financialOperationMapper = new FinancialOperationMapper();

    private User user;

    private List<FinancialOperation> financialOperations;

    @BeforeEach
    void setUp() {
        user = User
                .builder()
                .id(1L)
                .build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(user);

        financialOperationService = new FinancialOperationServiceImpl(financialOperationRepository, financialOperationMapper);

        FinancialOperation financialOperationOne = FinancialOperation
                .builder()
                .id(1L)
                .user(user)
                .costs(BigDecimal.ONE)
                .date(LocalDate.now())
                .build();

        FinancialOperation financialOperationTwo = FinancialOperation
                .builder()
                .id(2L)
                .user(user)
                .costs(BigDecimal.TEN)
                .date(LocalDate.now().minusDays(5))
                .build();

        financialOperations = new ArrayList<>();
        financialOperations.add(financialOperationOne);
        financialOperations.add(financialOperationTwo);
    }

    /**
     * {@link FinancialOperationServiceImpl#getAllUserOperations()}
     */
    @Test
    void getAllUserOperations_lookingForUserOperations_shouldReturnAllUserOperations() {
        when(financialOperationRepository.findAllByUserId(user.getId())).thenReturn(financialOperations);
        List<FinancialOperationDto> financialOperationDto = financialOperationService.getAllUserOperations();

        assertEquals(2, financialOperationDto.size());
    }

    /**
     * {@link FinancialOperationServiceImpl#getOperationFromDateInterval(String, String)}
     */
    @Test
    void getOperationFromDateInterval_statingInterval_shouldReturnAllOperationsBetween() {
        when(financialOperationRepository
                .findAllByUserAndDateBetween(user, LocalDate.now(), LocalDate.now().minusDays(5)))
                .thenReturn(Collections.singletonList(financialOperations.get(0)));

        List<FinancialOperationDto> financialOperationDtos = financialOperationService
                .getOperationFromDateInterval(LocalDate.now().toString(), LocalDate.now().minusDays(5).toString());

        assertAll(
                () -> verify(financialOperationRepository, times(1))
                        .findAllByUserAndDateBetween(user,LocalDate.now(), LocalDate.now().minusDays(5)),
                () -> assertEquals(1, financialOperationDtos.size())
        );

    }

    /**
     * {@link FinancialOperationServiceImpl#removeAllUserOperations()}
     */
    @Test
    void removeAllUserOperations_forGivenUser_shouldRemoveAllOperations() {
        financialOperationService.removeAllUserOperations();
        verify(financialOperationRepository, times(1)).removeAllByUserId(user.getId());
    }

    /**
     * {@link FinancialOperationServiceImpl#removeGivenUserOperations(List)}
     */
    @Test
    void removeGivenUserOperations_forGivenOperations_shouldRemoveThem() {
        List<Long> listOfIds = Arrays.asList(1L, 2L, 3L);
        financialOperationService.removeGivenUserOperations(listOfIds);
        verify(financialOperationRepository, times(1))
                .removeGroupOfOperationsByIdsAndUser(listOfIds, user);
    }

    @DisplayName("For save and update tests")
    @Nested
    class SaveAndUpdate {

        List<FinancialOperationDto> financialOperationDtoList;
        List<FinancialOperation> financialOperationsAfterMapping;

        @BeforeEach
        void setUp() {
            setFinancialOperationDtos();
            setFinancialOperationModels();
        }

        private void setFinancialOperationModels() {
            FinancialOperation financialOperationOne = FinancialOperation
                    .builder()
                    .id(1L)
                    .user(user)
                    .costs(new BigDecimal("5"))
                    .date(LocalDate.ofYearDay(2019, 200))
                    .build();

            FinancialOperation financialOperationTwo = FinancialOperation
                    .builder()
                    .id(2L)
                    .user(user)
                    .costs(new BigDecimal("7"))
                    .date(LocalDate.ofYearDay(2019, 150))
                    .build();

            financialOperationsAfterMapping = new ArrayList<>();
            financialOperationsAfterMapping.add(financialOperationOne);
            financialOperationsAfterMapping.add(financialOperationTwo);
        }

        private void setFinancialOperationDtos() {
            FinancialOperationDto financialOperationDtoOne = FinancialOperationDto
                    .builder()
                    .id(1L)
                    .costs("5")
                    .date(LocalDate.ofYearDay(2019, 200).toString())
                    .build();

            FinancialOperationDto financialOperationDtoTwo = FinancialOperationDto
                    .builder()
                    .id(2L)
                    .costs("7")
                    .date(LocalDate.ofYearDay(2019, 150).toString())
                    .build();

            financialOperationDtoList = new ArrayList<>();
            financialOperationDtoList.add(financialOperationDtoOne);
            financialOperationDtoList.add(financialOperationDtoTwo);
        }

        /**
         * {@link FinancialOperationServiceImpl#updateGivenUserOperations(List)}
         */
        @Test
        void updateGivenUserOperations_havingTwoToUpdate_shouldSaveUpdated() {
            when(financialOperationRepository.findById(1L)).thenReturn(Optional.of(financialOperations.get(0)));
            when(financialOperationRepository.findById(2L)).thenReturn(Optional.of(financialOperations.get(1)));
            financialOperationService.updateGivenUserOperations(financialOperationDtoList);

            verify(financialOperationRepository, times(1)).save(financialOperationsAfterMapping.get(0));
            verify(financialOperationRepository, times(1)).save(financialOperationsAfterMapping.get(1));
        }

        /**
         * {@link FinancialOperationServiceImpl#saveOperationsIntoUserAccount(List)}
         */
        @Test
        void saveOperationsIntoUserAccount_forGivenList_shouldSaveGivenOperations() {
            financialOperationService.saveOperationsIntoUserAccount(financialOperationDtoList);
            verify(financialOperationRepository, times(1)).saveAll(financialOperationsAfterMapping);
        }

    }
}