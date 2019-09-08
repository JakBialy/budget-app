package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for {@link UserRepository}
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User userOne = User
                .builder()
                .username("Kuba")
                .password("papapa")
                .build();

        User userTwo = User
                .builder()
                .username("Jakub")
                .password("alalala")
                .build();
        entityManager.persist(userOne);
        entityManager.persist(userTwo);
    }


    /**
     * {@link UserRepository#findOneByUsername(String)} (String)}
     */
    @Test
    void testFindByUsername_havingTwoUsers_shouldFindOneOfThem(){
        User result = userRepository.findOneByUsername("Kuba").orElse(new User());
        assertEquals("Kuba", result.getUsername());
    }


    /**
     * {@link UserRepository#findOneByUsername(String)} (String)}
     */
    @Test
    void testFindByUsername_searchingNotExisitngOne_shouldThrowException(){
        assertThrows(UsernameNotFoundException.class, () -> userRepository
                .findOneByUsername("XXXX").orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

}
