package linux.assignment.repository;

import linux.assignment.entity.Item;

public interface MainRepository {
    void save(Item item);

    Item findByLocation(String location);
}
