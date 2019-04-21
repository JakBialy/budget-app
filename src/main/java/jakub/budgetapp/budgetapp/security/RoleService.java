package jakub.budgetapp.budgetapp.security;


import jakub.budgetapp.budgetapp.entites.Role;

public interface RoleService {

    Role getOrCreate(String roleName);
}
