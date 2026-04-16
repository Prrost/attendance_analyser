package kz.diploma.rprettser.attendance_analyser.business.facade.impl;

import kz.diploma.rprettser.attendance_analyser.business.facade.AttendanceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceFacadeImpl implements AttendanceFacade {

    @Override
    public void finalizeExpiredLessons() {
        // TODO
    }

    @Override
    public void pushAttendanceToLms(Long attendanceId) {
        // TODO
    }
}
