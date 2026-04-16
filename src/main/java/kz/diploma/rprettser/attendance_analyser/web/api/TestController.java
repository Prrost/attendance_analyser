package kz.diploma.rprettser.attendance_analyser.web.api;

import kz.diploma.rprettser.attendance_analyser.business.facade.CacheSyncFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final CacheSyncFacade cacheSyncFacade;

    @GetMapping("/sync")
    public String sync() {
        cacheSyncFacade.syncAll();
        return "OK";
    }
}
