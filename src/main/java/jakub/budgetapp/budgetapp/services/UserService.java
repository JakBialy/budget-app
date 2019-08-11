package jakub.budgetapp.budgetapp.services;


import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.entites.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User loadUserByUserName(String username);
}
