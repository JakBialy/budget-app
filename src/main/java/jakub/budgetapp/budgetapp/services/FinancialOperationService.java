package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;

import java.util.List;

public interface FinancialOperationService {

    void saveOperationsIntoUserAccount(List<FinancialOperationDto> operations);
}
