package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.FinancialOperation;
import jakub.budgetapp.budgetapp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialOperationRepository extends JpaRepository<FinancialOperation, Long> {

    List<FinancialOperation> findAllByUserId (Long userId);

    List<FinancialOperation> findAllByUserAndDateBetween(User user, LocalDate start, LocalDate end);

    @Transactional
    void removeAllByUserId (Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FinancialOperation WHERE id IN (?1) and user = ?2")
    void removeGroupOfOperationsByIdsAndUser(List<Long> listOfOperationsId, User user);
}
