package linux.assignment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Item {
    private String itemId; // uuid
    private String location;
    private double latitude; // 위도
    private double longitude; // 경도
    private double excluUseAr; // 전용면적
    private String dealDay; // 계약 년월일
    private int dealAmount; // 거래금액 (만)
    private int floor; // 층
    private String buildYear; // 건축년도
    private String dealingGbn; // 거래유형
}
