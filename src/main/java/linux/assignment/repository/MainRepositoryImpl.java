package linux.assignment.repository;

import linux.assignment.entity.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

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
    public List<Item> findAllItems() {
        String sql = "SELECT * FROM items";
        return jdbcTemplate.query(sql, itemRowMapper(sql));
    }

    @Override
    public Item findByLocation(String location) {
        String sql = "SELECT longitude, latitude FROM items WHERE location = ?";
        List<Item> query = jdbcTemplate.query(sql, itemRowMapper(sql), location);
        return query.isEmpty() ? null : query.getFirst();
    }

    private RowMapper<Item> itemRowMapper(String sql) {
        String sqlColumns = sql.substring(0, sql.toLowerCase().indexOf("from"));
        return (rs, rowNum) -> {
            Item item = Item.builder().build();
            if(sqlColumns.contains("location") || sqlColumns.contains("*")) {
                item.setLocation(rs.getString("location"));
            }
            if (sqlColumns.contains("latitude") || sqlColumns.contains("*")) {
                item.setLatitude(rs.getDouble("latitude"));
            }
            if (sqlColumns.contains("longitude") || sqlColumns.contains("*")) {
                item.setLongitude(rs.getDouble("longitude"));
            }
            if (sqlColumns.contains("excluUseAr") || sqlColumns.contains("*")) {
                item.setExcluUseAr(rs.getDouble("excluUseAr"));
            }
            if (sqlColumns.contains("dealDay") || sqlColumns.contains("*")) {
                item.setDealDay(rs.getString("dealDay"));
            }
            if (sqlColumns.contains("dealAmount") || sqlColumns.contains("*")) {
                item.setDealAmount(rs.getInt("dealAmount"));
            }
            if (sqlColumns.contains("floor") || sqlColumns.contains("*")) {
                item.setFloor(rs.getInt("floor"));
            }
            if (sqlColumns.contains("buildYear") || sqlColumns.contains("*")) {
                item.setBuildYear(rs.getString("buildYear"));
            }
            if (sqlColumns.contains("dealingGbn") || sqlColumns.contains("*")) {
                item.setDealingGbn(rs.getString("dealingGbn"));
            }
            return item;
        };
    }
}
