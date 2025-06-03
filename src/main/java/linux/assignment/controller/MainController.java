package linux.assignment.controller;

import linux.assignment.dto.InfoListResponseDto;
import linux.assignment.dto.InfoResponseDto;
import linux.assignment.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {
    private final MainService mainService;

    @GetMapping("/infos")
    public ResponseEntity<InfoListResponseDto> infos() {
        List<InfoResponseDto> res = new ArrayList<>();
        res.add(InfoResponseDto.set(37.40143885692029, 126.92283848282376, 17.811, "2025-06.03", 12000, 12, "2002", "중개거래"));
        res.add(InfoResponseDto.set(37.40344412936734, 126.93093453474235, 17.811, "2025-06.03", 12000, 12, "2002", "중개거래"));
        InfoListResponseDto set = InfoListResponseDto.set(res);
        return ResponseEntity.ok(set);
    }

}
