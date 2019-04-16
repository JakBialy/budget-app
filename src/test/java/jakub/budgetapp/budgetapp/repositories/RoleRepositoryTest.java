package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link RoleRepository}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * {@link RoleRepository#findByName(String)}
     */
    @Test
    public void testFindByName_havingTwoRoles_shouldFindEachOfThemSeparately(){
        Role user = new Role();
        Role admin = new Role();

        user.setName("USER");
        admin.setName("ADMIN");

        entityManager.persist(user);
        entityManager.persist(admin);

        Role result = roleRepository.findByName("USER");
        assertEquals("USER", result.getName());
        assertEquals(1, result.getId());

        Role resultTwo = roleRepository.findByName("ADMIN");
        assertEquals("ADMIN", resultTwo.getName());
        assertEquals(2, resultTwo.getId());
    }
}
