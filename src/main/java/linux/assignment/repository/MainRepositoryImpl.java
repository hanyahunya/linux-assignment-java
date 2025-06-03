package linux.assignment.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class MainRepositoryImpl implements MainRepository {
    private final JdbcTemplate jdbcTemplate;

    public MainRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}
