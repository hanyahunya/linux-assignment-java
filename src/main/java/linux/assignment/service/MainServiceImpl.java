package linux.assignment.service;

import linux.assignment.dto.InfoListResponseDto;
import linux.assignment.dto.InfoResponseDto;
import linux.assignment.entity.Item;
import linux.assignment.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final MainRepository mainRepository;
    private final GeocodingService geocodingService;

    @Override
    public InfoListResponseDto getInfoList() {
        File folder = new File("/app/xml");
        File[] xmlFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        if (xmlFiles == null || xmlFiles.length == 0) {
            System.out.println("XML 파일이 없습니다.");
            return null;
        }

        List<InfoResponseDto> dtoList = new ArrayList<>();
        for (File xmlFile : xmlFiles) {
            List<Item> items = parseXml(xmlFile);
            for (Item item : items) {
                Item dbItem = mainRepository.findByLocation(item.getLocation());
                if (dbItem != null) {
                    item.setLatitude(dbItem.getLatitude());
                    item.setLongitude(dbItem.getLongitude());
                } else {
                    Map<String, Double> geocoded = geocodingService.geocode(item.getLocation());
                    item.setLatitude(geocoded.get("latitude"));
                    item.setLongitude(geocoded.get("longitude"));
                }
                mainRepository.save(item);

                dtoList.add(InfoResponseDto.set(
                        item.getLatitude(),
                        item.getLongitude(),
                        item.getExcluUseAr(),
                        item.getDealDay(),
                        item.getDealAmount(),
                        item.getFloor(),
                        item.getBuildYear(),
                        item.getDealingGbn()
                ));
            }
        }
        return InfoListResponseDto.set(dtoList);
    }

    private List<Item> parseXml(File file) {
        List<Item> itemListResult = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();

            NodeList itemList = doc.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Element item = (Element) itemList.item(i);

                String aptNm = getText(item, "aptNm");
                String umdNm = getText(item, "umdNm");
                String jibun = getText(item, "jibun");
                String address = "경기 안양시 동안구 " + umdNm + " " + jibun + " " + aptNm;

                double excluUseAr = Double.parseDouble(getText(item, "excluUseAr"));
                int dealAmount = Integer.parseInt(getText(item, "dealAmount").replace(",", ""));
                String buildYear = getText(item, "buildYear");
                String dealingGbn = getText(item, "dealingGbn");
                int floor = Integer.parseInt(getText(item, "floor"));

                String dealYear = getText(item, "dealYear");
                String dealMonth = getText(item, "dealMonth");
                String dealDay = getText(item, "dealDay");
                String dealDate = dealYear + "-" + dealMonth + "-" + dealDay;

                Item newItem = Item.builder()
                        .itemId(UUID.randomUUID().toString())
                        .location(address)
                        .excluUseAr(excluUseAr)
                        .dealDay(dealDate)
                        .dealAmount(dealAmount)
                        .floor(floor)
                        .buildYear(buildYear)
                        .dealingGbn(dealingGbn)
                        .build();

                itemListResult.add(newItem);
            }

        } catch (Exception e) {
            System.err.println("파일 처리 오류: " + file.getName());
            e.printStackTrace();
        }
        return itemListResult;
    }


    private String getText(Element item, String name) {
        NodeList nodeList = item.getElementsByTagName(name);
        if (nodeList == null || nodeList.getLength() == 0) return "";
        return nodeList.item(0).getTextContent().trim();
    }
}
