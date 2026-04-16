package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.AttendanceService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;
import kz.diploma.rprettser.attendance_analyser.dal.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;

    @Override
    public Attendance save(Attendance attendance) {
        // TODO
        return null;
    }

    @Override
    public Optional<Attendance> findByStudentAndLesson(Long studentId, Long lessonId) {
        // TODO
        return Optional.empty();
    }

    @Override
    public boolean existsByStudentAndLesson(Long studentId, Long lessonId) {
        // TODO
        return false;
    }

    @Override
    public Attendance createOrUpdate(Long studentId, Long lessonId, AttendanceMark mark) {
        // TODO
        return null;
    }
}
