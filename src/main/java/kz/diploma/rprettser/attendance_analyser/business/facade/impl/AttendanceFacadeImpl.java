package kz.diploma.rprettser.attendance_analyser.business.facade.impl;

import kz.diploma.rprettser.attendance_analyser.business.client.LmsClient;
import kz.diploma.rprettser.attendance_analyser.business.dto.lms.LmsSetAttendanceDto;
import kz.diploma.rprettser.attendance_analyser.business.facade.AttendanceFacade;
import kz.diploma.rprettser.attendance_analyser.business.service.AttendanceService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import kz.diploma.rprettser.attendance_analyser.dal.entity.FaceRecognitionEvent;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;
import kz.diploma.rprettser.attendance_analyser.dal.entity.StudentGroup;
import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;
import kz.diploma.rprettser.attendance_analyser.dal.repository.AttendanceRepository;
import kz.diploma.rprettser.attendance_analyser.dal.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceFacadeImpl implements AttendanceFacade {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceService attendanceService;
    private final LessonRepository lessonRepository;
    private final LmsClient lmsClient;

    @Value("${attendance.late-threshold-minutes:15}")
    private int lateThresholdMinutes;

    @Override
    @Transactional
    public void finalizeExpiredLessons() {
        LocalDateTime now = LocalDateTime.now();
        List<Lesson> lessons = lessonRepository.findLessonsToFinalize(now);

        if (lessons.isEmpty()) {
            return;
        }
        log.info("Finalizing {} expired lessons", lessons.size());

        for (Lesson lesson : lessons) {
            StudentGroup group = lesson.getStudentGroup();
            if (group == null) {
                continue;
            }
            for (Student student : group.getStudents()) {
                Optional<Attendance> attendanceOpt = attendanceService.findByStudentAndLesson(
                        student.getId(), lesson.getId());

                Attendance attendance;
                if (attendanceOpt.isEmpty()) {
                    attendance = attendanceService.createOrUpdate(student.getId(), lesson.getId(), AttendanceMark.ABSENT);
                } else {
                    attendance = attendanceOpt.get();
                    AttendanceMark mark = determineMark(attendance, lesson);
                    attendance.setMark(mark);
                    attendance.setUpdatedAt(now);
                    attendanceRepository.save(attendance);
                }

                try {
                    pushAttendanceToLms(attendance.getId());
                } catch (Exception e) {
                    log.error("Failed to push attendance id={} to LMS", attendance.getId(), e);
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateActiveAttendance() {
        LocalDateTime now = LocalDateTime.now();
        List<Lesson> lessons = lessonRepository.findInProgressLessons(now);
        if (lessons.isEmpty()) {
            return;
        }
        log.info("Updating attendance for {} in-progress lessons", lessons.size());

        for (Lesson lesson : lessons) {
            StudentGroup group = lesson.getStudentGroup();
            if (group == null) {
                continue;
            }
            for (Student student : group.getStudents()) {
                Optional<Attendance> attendanceOpt = attendanceService.findByStudentAndLesson(
                        student.getId(), lesson.getId());

                if (attendanceOpt.isEmpty()) {
                    continue;
                }

                Attendance attendance = attendanceOpt.get();
                AttendanceMark mark = determineMark(attendance, lesson);
                attendance.setMark(mark);
                attendance.setUpdatedAt(now);
                attendanceRepository.save(attendance);

                try {
                    pushAttendanceToLms(attendance.getId());
                } catch (Exception e) {
                    log.error("Failed to push active attendance id={} to LMS", attendance.getId(), e);
                }
            }
        }
    }

    @Override
    public void pushAttendanceToLms(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new NoSuchElementException("Attendance not found: " + attendanceId));

        Student student = attendance.getStudent();
        Lesson lesson = attendance.getLesson();

        LmsSetAttendanceDto dto = LmsSetAttendanceDto.builder()
                .studentName(student.getName() + " " + student.getLastName())
                .lessonName(lesson.getName())
                .attendanceMark(attendance.getMark())
                .build();

        lmsClient.setAttendance(dto);
        log.debug("Pushed attendance id={} to LMS: student={}, lesson={}, mark={}",
                attendanceId, dto.getStudentName(), dto.getLessonName(), dto.getAttendanceMark());
    }

    private AttendanceMark determineMark(Attendance attendance, Lesson lesson) {
        List<FaceRecognitionEvent> events = attendance.getFaceRecognitionEvents();
        if (events == null || events.isEmpty()) {
            return AttendanceMark.ABSENT;
        }
        LocalDateTime earliest = events.stream()
                .map(FaceRecognitionEvent::getRecognizedAt)
                .min(Comparator.naturalOrder())
                .orElse(null);

        if (earliest == null) {
            return AttendanceMark.ABSENT;
        }
        LocalDateTime lateThreshold = lesson.getStartsAt().plusMinutes(lateThresholdMinutes);
        return earliest.isAfter(lateThreshold) ? AttendanceMark.LATE : AttendanceMark.PRESENT;
    }
}
