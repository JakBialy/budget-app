package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialOperationRepository extends JpaRepository<FinancialOperation, Long> {

}
