package kz.diploma.rprettser.attendance_analyser.scheduler;

import kz.diploma.rprettser.attendance_analyser.business.facade.AttendanceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AttendanceFinalizationScheduler {

    private final AttendanceFacade attendanceFacade;

    @Scheduled(fixedRateString = "${scheduler.finalization.rate-ms:60000}")
    public void finalize() {
        // TODO
    }
}
