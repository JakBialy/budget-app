package jakub.budgetapp.budgetapp.services;


import jakub.budgetapp.budgetapp.entites.Role;

public interface RoleService {

    Role getOrCreate(String roleName);
}
