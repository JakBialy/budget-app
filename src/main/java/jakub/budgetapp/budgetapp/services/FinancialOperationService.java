package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;

import java.util.List;

public interface FinancialOperationService {

    void saveOperationsIntoUserAccount(List<FinancialOperationDto> operations);

    List<FinancialOperationDto> getAllUserOperations();

    List<FinancialOperationDto> getOperationFromDateInterval (String start, String end);

    void removeAllUserOperations();

    void removeGivenUserOperations(List<Long> financialOperationIds);

    void updateGivenUserOperations(List<FinancialOperationDto> financialOperationDto);
}
