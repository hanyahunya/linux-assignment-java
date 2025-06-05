package linux.assignment.repository;

import linux.assignment.entity.Item;

import java.util.List;

public interface MainRepository {
    void save(Item item);

    List<Item> findAllItems();

    Item findByLocation(String location);
}
