package jakub.budgetapp.budgetapp.dtos;

import lombok.*;

import jakub.budgetapp.budgetapp.entites.User;

import javax.validation.constraints.NotEmpty;

/**
 * Dto class for {@link User}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;

    // How to store it better as String is not the safest option?
    private String password;
}
