package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.mappers.implementations.FinancialOperationMapper;
import jakub.budgetapp.budgetapp.repositories.FinancialOperationRepository;
import jakub.budgetapp.budgetapp.services.FinancialOperationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialOperationServiceImpl implements FinancialOperationService {

    private FinancialOperationRepository financialOperationRepository;
    private FinancialOperationMapper financialOperationMapper;
    private UserServiceImpl userService;


    public FinancialOperationServiceImpl(FinancialOperationRepository financialOperationRepository,
                                         FinancialOperationMapper financialOperationMapper,
                                         UserServiceImpl userService) {
        this.financialOperationRepository = financialOperationRepository;
        this.financialOperationMapper = financialOperationMapper;
        this.userService = userService;
    }

    @Override
    public void saveOperationsIntoUserAccount(List<FinancialOperationDto> operations) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        List<FinancialOperation> financialOperations = new ArrayList<>();
        operations.forEach(x -> financialOperations.add(financialOperationMapper.toEntity(x)));
        financialOperations.forEach(x -> x.setUser(userService.loadUserByUserName(userName)));
        financialOperationRepository.saveAll(financialOperations);
    }
}
