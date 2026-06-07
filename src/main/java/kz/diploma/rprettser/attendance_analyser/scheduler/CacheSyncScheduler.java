package kz.diploma.rprettser.attendance_analyser.scheduler;

import kz.diploma.rprettser.attendance_analyser.business.facade.CacheSyncFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheSyncScheduler {

    private final CacheSyncFacade cacheSyncFacade;

    @Scheduled(fixedRateString = "${scheduler.cache-sync.rate-ms:60000}")
    public void sync() {
        log.info("Scheduled cache sync started");
        try {
            cacheSyncFacade.syncAll();
        } catch (Exception e) {
            log.error("Cache sync failed", e);
        }
    }
}
