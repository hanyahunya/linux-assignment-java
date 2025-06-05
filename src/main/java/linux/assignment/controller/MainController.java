package linux.assignment.controller;

import linux.assignment.dto.InfoListResponseDto;
import linux.assignment.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {
    private final MainService mainService;

    @GetMapping("/infos")
    public ResponseEntity<InfoListResponseDto> infos(@RequestParam(required = false) String floor) {
        InfoListResponseDto infoList = mainService.getInfoList();
        return ResponseEntity.ok(infoList);
    }

    @GetMapping("update") // curl http://linux-java:8080/api/update
    public void dd(){}


}
