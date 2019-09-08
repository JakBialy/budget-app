package jakub.budgetapp.budgetapp.controllers;

import jakub.budgetapp.budgetapp.dtos.FinancialOperationDto;
import jakub.budgetapp.budgetapp.services.FinancialOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation-controller")
public class UserOperationController {

    private FinancialOperationService financialOperationService;

    public UserOperationController(FinancialOperationService financialOperationService) {
        this.financialOperationService = financialOperationService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-all-operations")
    public void saveAllUserOperations(@RequestBody List<FinancialOperationDto> listOfOperations) {
        financialOperationService.saveOperationsIntoUserAccount(listOfOperations);
    }
    // ------------- CREATE --------------

    // ------------- READ --------------
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get-all-operations")
    public List<FinancialOperationDto> getAllUserOperations() {
        return financialOperationService.getAllUserOperations();
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/get-operations-from-interval")
    public List<FinancialOperationDto> getDateFromInterval(@RequestParam String start, @RequestParam String end) {
        return financialOperationService.getOperationFromDateInterval(start, end);
    }

    // ------------- UPDATE --------------
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update-given-operations")
    public void updateGivenUserOperations(@RequestBody List<FinancialOperationDto> listOfOperations) {
        financialOperationService.updateGivenUserOperations(listOfOperations);
    }

    // ------------- DELETE --------------
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/remove-all-operations")
    public void removeAllUserOperations() {
        financialOperationService.removeAllUserOperations();
    }
    @ResponseStatus(HttpStatus.OK)

    @DeleteMapping("/remove-given-operations")
    public void removeGivenUserOperations(@RequestBody List<Long> listOfOperationsId) {
        financialOperationService.removeGivenUserOperations(listOfOperationsId);
    }
}
