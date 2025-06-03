package linux.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "set")
public class InfoListResponseDto {
    private List<InfoResponseDto> infos;
}
