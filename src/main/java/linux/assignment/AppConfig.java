package linux.assignment;

import linux.assignment.repository.MainRepository;
import linux.assignment.repository.MainRepositoryImpl;
import linux.assignment.service.GeocodingService;
import linux.assignment.service.MainService;
import linux.assignment.service.MainServiceImpl;
import linux.assignment.service.NaverGeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    private final DataSource dataSource;

    @Autowired
    public AppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MainService mainService() {
        return new MainServiceImpl(mainRepository(), geocodingService());
    }
    @Bean
    public GeocodingService geocodingService() {
        return new NaverGeocodingService();
    }

    @Bean
    public MainRepository mainRepository() {
        return new MainRepositoryImpl(dataSource);
    }
}
