package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link UserRepository}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
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
    public void testFindByUsername_havingTwoUsers_shouldFindOneOfThem(){
        User result = userRepository.findOneByUsername("Kuba").orElse(new User());
        assertEquals("Kuba", result.getUsername());
    }


    /**
     * {@link UserRepository#findOneByUsername(String)} (String)}
     */
    @Test(expected = UsernameNotFoundException.class)
    public void testFindByUsername_havingTwoUsers_shouldThrowException(){
        User result = userRepository.findOneByUsername("XXXX").orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
