package linux.assignment.repository;

import linux.assignment.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

public class MainRepositoryImpl implements MainRepository {
    private final JdbcTemplate jdbcTemplate;

    public MainRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Item item) {
        String sql = "INSERT INTO items (item_id, location, latitude, longitude, excluUseAr, dealDay, dealAmount, floor, buildYear, dealingGbn) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getItemId());
            ps.setString(2, item.getLocation());
            ps.setDouble(3, item.getLatitude());
            ps.setDouble(4, item.getLongitude());
            ps.setDouble(5, item.getExcluUseAr());
            ps.setString(6, item.getDealDay());
            ps.setDouble(7, item.getDealAmount());
            ps.setDouble(8, item.getFloor());
            ps.setString(9, item.getBuildYear());
            ps.setString(10, item.getDealingGbn());
            return ps;
        });
    }

    @Override
    public Item findByLocation(String location) {
        String sql = "SELECT longitude, latitude FROM items WHERE location = ?";
        List<Item> query = jdbcTemplate.query(sql, itemRowMapper(), location);
        return query.isEmpty() ? null : query.getFirst();
    }

    private RowMapper<Item> itemRowMapper() {
        return (rs, rowNum) -> {
            return Item.builder()
                    .latitude(rs.getDouble("latitude"))
                    .longitude(rs.getDouble("longitude"))
                    .build();
        };
    }
}
