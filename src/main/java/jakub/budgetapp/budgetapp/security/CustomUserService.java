package jakub.budgetapp.budgetapp.security;


import jakub.budgetapp.budgetapp.entites.User;
import jakub.budgetapp.budgetapp.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findOneByUsername(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userOptional.map(CustomUserDetails::new).get();
    }
}
