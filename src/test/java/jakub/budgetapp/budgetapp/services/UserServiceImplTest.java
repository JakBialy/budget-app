package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.repositories.UserRepository;
import jakub.budgetapp.budgetapp.security.RoleService;
import jakub.budgetapp.budgetapp.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * Test class for {@link UserServiceImpl}
 */
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    private static final String DEFAULT_USER_ROLE_NAME = "USER";

    @Mock
    UserRepository userRepository;

    @Mock
    RoleService roleService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userRepository, roleService, bCryptPasswordEncoder);
    }

    /**
     * Should save user
     * {@link UserServiceImpl#saveUser(UserDto)}
     */
    @Test
    void saveUser_savingUserWithGivenDetails_shouldSaveUser() {
        UserDto userDto = UserDto.builder()
                .password("123")
                .username("test_user")
                .build();

        userService.saveUser(userDto);

        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(roleService, times(1)).getOrCreate(DEFAULT_USER_ROLE_NAME);
        verify(userRepository, times(1)).save(any());
    }

}