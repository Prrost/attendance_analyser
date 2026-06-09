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
    public void finalizeExpiredLessons() {
        log.debug("Attendance finalization started");
        try {
            attendanceFacade.finalizeExpiredLessons();
        } catch (Exception e) {
            log.error("Attendance finalization failed", e);
        }
    }

    @Scheduled(fixedRateString = "${scheduler.active-attendance.rate-ms:5000}")
    public void updateActiveAttendance() {
        log.debug("Active attendance update started");
        try {
            attendanceFacade.updateActiveAttendance();
        } catch (Exception e) {
            log.error("Active attendance update failed", e);
        }
    }
}
