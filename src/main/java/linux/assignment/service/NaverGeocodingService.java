package linux.assignment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class NaverGeocodingService implements GeocodingService {

    @Value("${api.key.id}")
    private String apiKeyId;

    @Value("${api.key}")
    private String apiKey;

    @Override
    public Map<String, Double> geocode(String address) {
        Map<String, Double> result = new HashMap<>();

        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String apiUrl = "https://maps.apigw.ntruss.com/map-geocode/v2/geocode?query=" + encodedAddress;

            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // 헤더 설정
            con.setRequestProperty("x-ncp-apigw-api-key-id", apiKeyId);
            con.setRequestProperty("x-ncp-apigw-api-key", apiKey);
            con.setRequestProperty("Accept", "application/json");

            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.toString());
            JsonNode addresses = root.path("addresses");

            if (addresses.isArray() && !addresses.isEmpty()) {
                JsonNode firstAddress = addresses.get(0);
                double latitude = firstAddress.path("y").asDouble();  // 위도
                double longitude = firstAddress.path("x").asDouble(); // 경도

                result.put("latitude", latitude);
                result.put("longitude", longitude);
            } else {
                System.out.println("주소 결과 없음.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
