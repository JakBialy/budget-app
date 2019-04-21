package jakub.budgetapp.budgetapp.dtos;

import lombok.*;

import jakub.budgetapp.budgetapp.entites.User;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

/**
 * Dto class for {@link User}
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty
    @Column(unique = true)
    private String username;

    // How to store it better as String is not the safest option?
    @NotEmpty
    private String password;
}
