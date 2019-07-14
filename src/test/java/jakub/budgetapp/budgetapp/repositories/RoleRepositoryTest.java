package jakub.budgetapp.budgetapp.repositories;

import jakub.budgetapp.budgetapp.entites.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link RoleRepository}
 */
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * {@link RoleRepository#findByName(String)}
     */
    @Test
    void testFindByName_havingTwoRoles_shouldFindEachOfThemSeparately(){
        Role user = new Role();
        Role admin = new Role();

        user.setName("USER");
        admin.setName("ADMIN");

        entityManager.persist(user);
        entityManager.persist(admin);

        Role result = roleRepository.findByName("USER");
        assertEquals("USER", result.getName());

        Role resultTwo = roleRepository.findByName("ADMIN");
        assertEquals("ADMIN", resultTwo.getName());
    }
}
