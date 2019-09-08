package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.mappers.implementations.FinancialOperationMapper;
import jakub.budgetapp.budgetapp.repositories.FinancialOperationRepository;
import jakub.budgetapp.budgetapp.services.FinancialOperationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private FinancialOperationRepository financialOperationRepository;
    private FinancialOperationMapper financialOperationMapper;


    public FinancialOperationServiceImpl(FinancialOperationRepository financialOperationRepository,
                                         FinancialOperationMapper financialOperationMapper) {
        this.financialOperationRepository = financialOperationRepository;
        this.financialOperationMapper = financialOperationMapper;
    }

    @Override
    public void saveOperationsIntoUserAccount(List<FinancialOperationDto> operations) {
        User customUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FinancialOperation> financialOperations = operations
                .stream()
                .map(financialOperationMapper::toEntity)
                .collect(Collectors.toList());

        financialOperations.forEach(x -> x.setUser(customUser));
        financialOperationRepository.saveAll(financialOperations);
    }

    @Override
    public List<FinancialOperationDto> getAllUserOperations() {
        User customUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return financialOperationRepository
                .findAllByUserId(customUser.getId())
                .stream()
                .map(financialOperationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FinancialOperationDto> getOperationFromDateInterval(String start, String end) {
        User customUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return financialOperationRepository
                .findAllByUserAndDateBetween(customUser, LocalDate.parse(start), LocalDate.parse(end))
                .stream()
                .map(financialOperationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAllUserOperations() {
        User customUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        financialOperationRepository.removeAllByUserId(customUser.getId());
    }

    @Override
    public void removeGivenUserOperations(List<Long> financialOperationIds) {
        User customUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        financialOperationRepository.removeGroupOfOperationsByIdsAndUser(financialOperationIds, customUser);
    }

    @Override
    public void updateGivenUserOperations(List<FinancialOperationDto> financialOperationDtoList) {

        financialOperationDtoList
                .forEach(x -> financialOperationRepository
                        .findById(x.getId())
                        .ifPresent(y -> {
                            y.setCategory(x.getCategory());
                            y.setCosts(new BigDecimal(x.getCosts()));
                            y.setDescription(x.getDescription());
                            y.setCurrency(x.getCurrency());
                            y.setDate(LocalDate.parse(x.getDate()));
                            financialOperationRepository.save(y);
                        }));

    }
}