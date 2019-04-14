package jakub.budgetapp.budgetapp.dtos;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotEmpty
    @Column(unique = true)
    private String username;

    // TODO
    // How to store it better as String is not the safest option?
    @NotEmpty
    private String password;
}
