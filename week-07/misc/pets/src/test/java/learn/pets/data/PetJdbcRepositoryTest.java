package learn.pets.data;

import learn.pets.models.Pet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PetJdbcRepositoryTest {

    @Autowired
    PetJdbcRepository repository;

    @Test
    void shouldFindAll() {
        List<Pet> pets = repository.findAll();
        // Not really part of the test.
        // But it's nice to confirm in the console.
        System.out.println(pets);

        assertNotNull(pets);
        assertEquals(3, pets.size());
    }

    @Test
    void shouldFindById() {

        Pet expected = new Pet();
        expected.setPetId(3);
        expected.setName("Noodles");
        expected.setType("Dog");

        Pet actual = repository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByName() {

        Pet expected = new Pet();
        expected.setPetId(3);
        expected.setName("Noodles");
        expected.setType("Dog");

        List<Pet> actual = repository.findByName("Noodles");
        assertEquals(1, actual.size());
        assertEquals(expected, actual.get(0));
    }

    @Test
    void shouldAdd() {
        Pet pet = new Pet();
        pet.setName("Mer");
        pet.setType("Hamster");

        Pet actual = repository.add(pet);

        System.out.println(actual);
        assertNotNull(actual);
        assertTrue(actual.getPetId() > 0);
    }

    @Test
    void shouldUpdateExisting() {
        Pet pet = new Pet();
        pet.setPetId(2);
        pet.setName("Singe");
        pet.setType("Snake");

        assertTrue(repository.update(pet));
        assertEquals(pet, repository.findById(2));
    }

    @Test
    void shouldNotUpdateMissing() {
        Pet pet = new Pet();
        pet.setPetId(20000);
        pet.setName("Singe");
        pet.setType("Snake");

        assertFalse(repository.update(pet));
    }

    @Test
    void shouldDeleteExisting() {

        assertTrue(repository.deleteById(4));
    }

    @Test
    void shouldNotDeleteMissing() {
        assertFalse(repository.deleteById(40000));
    }
}