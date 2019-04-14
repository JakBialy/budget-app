package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
