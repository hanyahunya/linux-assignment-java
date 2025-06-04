package linux.assignment.service;

import java.util.Map;

public interface GeocodingService {
    Map<String, Double> geocode(String address);
}
