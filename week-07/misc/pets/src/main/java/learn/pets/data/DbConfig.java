package learn.pets.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Bean
    public DataSource getDataSource() {
        MysqlDataSource result = new MysqlDataSource();
        // Url is for the test database.
        result.setUrl("jdbc:mysql://localhost:3306/pets");
        result.setUser("root");
        result.setPassword("top-secret-password");
        return result;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}