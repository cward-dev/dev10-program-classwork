package learn.sf.data;

import learn.sf.model.Panel;
import learn.sf.model.PanelMaterial;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Profile("jdbc-template")
public class PanelJdbcTemplateRepository implements PanelRepository {

    private final JdbcTemplate jdbcTemplate;

    public PanelJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Panel> mapper = (resultSet, rowNum) -> {
        Panel panel = new Panel();
        panel.setPanelId(resultSet.getInt("panel_id"));
        panel.setSection(resultSet.getString("section"));
        panel.setRow(resultSet.getInt("row"));
        panel.setColumn(resultSet.getInt("column"));
        panel.setYearInstalled(resultSet.getInt("year_installed"));
        panel.setMaterial(PanelMaterial.valueOf(resultSet.getString("material")));
        panel.setTracking(resultSet.getInt("tracking") == 1);
        return panel;
    };

    @Override
    public List<Panel> findAll() {
        final String sql = "select panel_id, section, `row`, `column`, year_installed, material, tracking from solar_panel;";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Panel> findBySection(String section) {
        final String sql = "select panel_id, section, `row`, `column`, year_installed, material, tracking from solar_panel where section = ?;";
        try {
            return jdbcTemplate.query(sql, mapper, section);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Panel> findByMaterial(PanelMaterial material) {
        final String sql = "select panel_id, section, `row`, `column`, year_installed, material, tracking from solar_panel where material = ?;";
        try {
            return jdbcTemplate.query(sql, mapper, material.name());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Panel findById(int panelId) {
        final String sql = "select panel_id, section, `row`, `column`, year_installed, material, tracking from solar_panel where panel_id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, mapper, panelId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Panel findByLocation(String section, int row, int column) {
        final String sql = "select panel_id, section, `row`, `column`, year_installed, material, tracking from solar_panel where section = ? and `row` = ? and `column` = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, mapper, section, row, column);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<String> getAllSections() {
        final String sql = "select distinct section from solar_panel order by section asc;";
        try {
            return jdbcTemplate.query(sql, (resultSet, rowNum) -> resultSet.getString("section"));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Panel add(Panel panel) {
        final String sql = "insert into solar_panel (section, `row`, `column`, year_installed, material, tracking) values (?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, panel.getSection());
            ps.setInt(2, panel.getRow());
            ps.setInt(3, panel.getColumn());
            ps.setInt(4, panel.getYearInstalled());
            ps.setString(5, panel.getMaterial().name());
            ps.setBoolean(6, panel.isTracking());

            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        panel.setPanelId(keyHolder.getKey().intValue());
        return panel;
    }

    @Override
    public boolean update(Panel panel) {
        final String sql = "update solar_panel set "
                + "section = ?, "
                + "`row` = ?, "
                + "`column` = ?, "
                + "year_installed = ?, "
                + "material = ?, "
                + "tracking = ? "
                + "where panel_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                panel.getSection(),
                panel.getRow(),
                panel.getColumn(),
                panel.getYearInstalled(),
                panel.getMaterial().name(),
                panel.isTracking(),
                panel.getPanelId());

        return rowsUpdated > 0;
    }

    @Override
    public boolean deleteById(int panelId) {
        final String sql = "delete from solar_panel where panel_id = ?;";
        return jdbcTemplate.update(sql, panelId) > 0;
    }
}
