package learn.pets.data;

import learn.pets.models.Pet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PetJdbcTemplateRepository implements PetRepository {

    private final JdbcTemplate jdbcTemplate;

    public PetJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Create a single mapper for all find methods.
    private final RowMapper<Pet> mapper = (resultSet, rowNum) -> {
        Pet pet = new Pet();
        pet.setPetId(resultSet.getInt("pet_id"));
        pet.setName(resultSet.getString("name"));
        pet.setType(resultSet.getString("type"));
        return pet;
    };

    @Override
    public List<Pet> findAll() {
        final String sql = "select pet_id, `name`, `type` from pet;";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Pet findById(int petId) {
        final String sql = "select pet_id, `name`, `type` from pet where pet_id = ?;";
        try {
            // 2. Parameters always follow SQL and mappers.
            // Any number of parameters is allowed.
            return jdbcTemplate.queryForObject(sql, mapper, petId);
        } catch (EmptyResultDataAccessException ex) {
            // 3. queryForObject can throw an unchecked exception
            // If the ResultSet is empty, it just means the pet with the id wasn't found.
            // So returning null is valid.
            return null;
        }
    }

    @Override
    public List<Pet> findByName(String petName) {
        final String sql = "select pet_id, `name`, `type` from pet where `name` = ?;";
        try {
            // 2. Parameters always follow SQL and mappers.
            // Any number of parameters is allowed.
            return jdbcTemplate.query(sql, mapper, petName);
        } catch (EmptyResultDataAccessException ex) {
            // 3. queryForObject can throw an unchecked exception
            // If the ResultSet is empty, it just means the pet with the id wasn't found.
            // So returning null is valid.
            return null;
        }
    }

    @Override
    public Pet add(Pet pet) {
        if (pet.getName() == null) {
            return null;
        }

        final String sql = "insert into pet (`name`, `type`) values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pet.getName());
            ps.setString(2, pet.getType());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        pet.setPetId(keyHolder.getKey().intValue());
        return pet;
    }

    @Override
    public boolean update(Pet pet) {
        if (pet.getName() == null) {
            return false;
        }

        final String sql = "update pet set "
                + "`name` = ?, "
                + "`type` = ? "
                + "where pet_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                pet.getName(), pet.getType(), pet.getPetId());

        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteById(int petId) {
        final String sql = "delete from pet where pet_id = ?;";
        return jdbcTemplate.update(sql, petId) > 0;
    }
}
