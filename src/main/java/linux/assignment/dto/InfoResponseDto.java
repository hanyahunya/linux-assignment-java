package linux.assignment.dto;

import linux.assignment.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "set")
public class InfoResponseDto {
    private double latitude; // 위도
    private double longitude; // 경도
    private double excluUseAr; // 전용면적
    private String dealDay; // 계약 년월일
    private int dealAmount; // 거래금액 (만)
    private int floor; // 층
    private String buildYear; // 건축년도
    private String dealingGbn; // 거래유형

    public static InfoResponseDto entityToDto(Item item) {
        return InfoResponseDto.set(
                item.getLatitude(),
                item.getLongitude(),
                item.getExcluUseAr(),
                item.getDealDay(),
                item.getDealAmount(),
                item.getFloor(),
                item.getBuildYear(),
                item.getDealingGbn()
        );
    }
}
