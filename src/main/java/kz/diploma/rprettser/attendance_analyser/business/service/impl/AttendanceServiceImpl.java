package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.AttendanceService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;
import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;
import kz.diploma.rprettser.attendance_analyser.dal.repository.AttendanceRepository;
import kz.diploma.rprettser.attendance_analyser.dal.repository.LessonRepository;
import kz.diploma.rprettser.attendance_analyser.dal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository repository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    @Override
    public Attendance save(Attendance attendance) {
        LocalDateTime now = LocalDateTime.now();
        if (attendance.getCreatedAt() == null) {
            attendance.setCreatedAt(now);
        }
        attendance.setUpdatedAt(now);
        return repository.save(attendance);
    }

    @Override
    public Optional<Attendance> findByStudentAndLesson(Long studentId, Long lessonId) {
        return repository.findByStudentIdAndLessonIdAndIsDeletedFalse(studentId, lessonId);
    }

    @Override
    public boolean existsByStudentAndLesson(Long studentId, Long lessonId) {
        return repository.existsByStudentIdAndLessonIdAndIsDeletedFalse(studentId, lessonId);
    }

    @Override
    public Attendance findOrCreate(Long studentId, Long lessonId) {
        return repository.findByStudentIdAndLessonIdAndIsDeletedFalse(studentId, lessonId)
                .orElseGet(() -> {
                    Student student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new NoSuchElementException("Student not found: " + studentId));
                    Lesson lesson = lessonRepository.findById(lessonId)
                            .orElseThrow(() -> new NoSuchElementException("Lesson not found: " + lessonId));
                    LocalDateTime now = LocalDateTime.now();
                    return repository.save(Attendance.builder()
                            .student(student)
                            .lesson(lesson)
                            .isDeleted(false)
                            .createdAt(now)
                            .updatedAt(now)
                            .build());
                });
    }

    @Override
    public Attendance createOrUpdate(Long studentId, Long lessonId, AttendanceMark mark) {
        Attendance attendance = findOrCreate(studentId, lessonId);
        attendance.setMark(mark);
        attendance.setUpdatedAt(LocalDateTime.now());
        return repository.save(attendance);
    }
}
