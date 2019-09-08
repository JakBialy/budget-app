package jakub.budgetapp.budgetapp.services.implementations;

import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.entites.Role;
import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.repositories.UserRepository;
import jakub.budgetapp.budgetapp.security.RoleService;
import jakub.budgetapp.budgetapp.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_USER_ROLE_NAME = "USER";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleService.getOrCreate(DEFAULT_USER_ROLE_NAME);

        // maybe here should be kind of a mapper?
        // not sure as it is only a registration
        User user = User
                .builder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .roles(new HashSet<>(Collections.singletonList(role)))
                .username(userDto.getUsername())
                .build();

        userRepository.save(user);
    }

}
