package jakub.budgetapp.budgetapp.controllers;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.FinancialOperationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operation-controller")
public class UserOperationController {

    private FinancialOperationService financialOperationService;

    public UserOperationController(FinancialOperationService financialOperationService) {
        this.financialOperationService = financialOperationService;
    }

    @PostMapping("/user-operations-to-save")
    public ResponseEntity receivingUserOperations(@RequestBody List<FinancialOperationDto> listOfOperations){
        financialOperationService.saveOperationsIntoUserAccount(listOfOperations);

        return ResponseEntity
                .ok()
                .build();
    }
}
