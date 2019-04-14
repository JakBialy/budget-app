package jakub.budgetapp.budgetapp.services;

import jakub.budgetapp.budgetapp.dtos.UserDto;
import jakub.budgetapp.budgetapp.entites.Role;
import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@javax.transaction.Transactional
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

        String password = (passwordEncoder.encode(userDto.getPassword()));
        Role role = roleService.getOrCreate(DEFAULT_USER_ROLE_NAME);
        Set<Role> roles = new HashSet<>(Collections.singletonList(role));

        // maybe here should be kind of a mapper?
        // not sure as it is only a registration
        User user = User
                .builder()
                .password(password)
                .roles(roles)
                .username(userDto.getUsername())
                .build();

        userRepository.save(user);
    }


}
