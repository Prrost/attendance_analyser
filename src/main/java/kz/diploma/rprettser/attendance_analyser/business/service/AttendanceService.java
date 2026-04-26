package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;

import java.util.Optional;

public interface AttendanceService {

    Attendance save(Attendance attendance);

    Optional<Attendance> findByStudentAndLesson(Long studentId, Long lessonId);

    boolean existsByStudentAndLesson(Long studentId, Long lessonId);

    Attendance findOrCreate(Long studentId, Long lessonId);

    Attendance createOrUpdate(Long studentId, Long lessonId, AttendanceMark mark);
}
