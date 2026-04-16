package kz.diploma.rprettser.attendance_analyser.business.facade;

public interface AttendanceFacade {

    void finalizeExpiredLessons();

    void pushAttendanceToLms(Long attendanceId);
}
