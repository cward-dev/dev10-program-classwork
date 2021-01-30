package learn.field_agent.data;

import learn.field_agent.models.Location;
import learn.field_agent.models.SecurityClearance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SecurityClearanceJdbcTemplateRepositoryTest {

    final static int NEXT_SECURITY_CLEARANCE_ID = 4;

    @Autowired
    SecurityClearanceJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    /*
    Testing Strategy:
    * Id 1 ("Secret") will remain unaltered
    * Id 2 ("Top Secret") will update from "Top Secret" to "Test Security Clearance"
    * Id 3 ("Ultra Top Secret") will delete
     */

    @Test
    void shouldFindAll() {
        List<SecurityClearance> securityClearances = repository.findAll();

        assertNotNull(securityClearances);
        assertTrue(securityClearances.size() > 0);
    }

    @Test
    void shouldFindById() {
        SecurityClearance secret = repository.findById(1);
        assertEquals("Secret", secret.getName());


        SecurityClearance invalidSecurityClearance = repository.findById(45);
        assertNull(invalidSecurityClearance);
    }

    @Test
    void shouldAdd() {
        SecurityClearance securityClearance = makeSecurityClearance();
        SecurityClearance actual = repository.add(securityClearance);
        assertNotNull(actual);
        assertEquals(NEXT_SECURITY_CLEARANCE_ID, actual.getSecurityClearanceId());
    }

    @Test
    void shouldUpdate() {
        SecurityClearance securityClearance = makeSecurityClearance();
        securityClearance.setSecurityClearanceId(2);
        assertTrue(repository.update(securityClearance));
        securityClearance.setSecurityClearanceId(16);
        assertFalse(repository.update(securityClearance));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(3));
        assertFalse(repository.deleteById(45));
    }

    SecurityClearance makeSecurityClearance() {
        SecurityClearance securityClearance = new SecurityClearance();
        securityClearance.setName("Test Security Clearance");
        return securityClearance;
    }
}