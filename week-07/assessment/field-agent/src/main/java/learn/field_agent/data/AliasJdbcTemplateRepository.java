package learn.field_agent.data;

import learn.field_agent.data.mappers.AliasMapper;
import learn.field_agent.models.Alias;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class AliasJdbcTemplateRepository implements AliasRepository {

    private final JdbcTemplate jdbcTemplate;

    public AliasJdbcTemplateRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<Alias> findAll() {
        final String sql = "select alias_id, `name`, persona from alias limit 1000;";
        return jdbcTemplate.query(sql, new AliasMapper());
    }

    @Override
    public Alias findById(int aliasId) {
        final String sql = "select alias_id, `name`, persona "
                + "from alias "
                + "where alias_id = ?;";

        return jdbcTemplate.query(sql, new AliasMapper(), aliasId).stream()
                .findAny().orElse(null);
    }

    @Override
    public Alias add(Alias alias) {
        final String sql = "insert into alias (`name`, persona) values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, alias.getName());
            ps.setString(2, alias.getPersona());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        alias.setAliasId(keyHolder.getKey().intValue());
        return alias;
    }

    @Override
    public boolean update(Alias alias) {

        final String sql = "update alias set "
                + "`name` = ?, "
                + "persona = ? "
                + "where alias_id = ?";

        return jdbcTemplate.update(sql, alias.getName(), alias.getPersona(), alias.getAliasId()) > 0;
    }

    @Override
    public boolean deleteById(int aliasId) {
        return jdbcTemplate.update("delete from alias where alias_id = ?", aliasId) > 0;
    }
}
