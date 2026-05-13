package kz.diploma.rprettser.attendance_analyser.business.facade.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.diploma.rprettser.attendance_analyser.business.dto.kafka.FaceRecognitionEventDto;
import kz.diploma.rprettser.attendance_analyser.business.facade.FaceRecognitionFacade;
import kz.diploma.rprettser.attendance_analyser.business.service.AttendanceService;
import kz.diploma.rprettser.attendance_analyser.business.service.ClassroomService;
import kz.diploma.rprettser.attendance_analyser.business.service.FaceRecognitionEventService;
import kz.diploma.rprettser.attendance_analyser.business.service.StudentService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Classroom;
import kz.diploma.rprettser.attendance_analyser.dal.entity.FaceRecognitionEvent;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;
import kz.diploma.rprettser.attendance_analyser.dal.enums.FaceRecognitionStatus;
import kz.diploma.rprettser.attendance_analyser.dal.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceRecognitionFacadeImpl implements FaceRecognitionFacade {

    private final FaceRecognitionEventService faceRecognitionEventService;
    private final AttendanceService attendanceService;
    private final StudentService studentService;
    private final ClassroomService classroomService;
    private final LessonRepository lessonRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void processEvent(FaceRecognitionEventDto dto) {
        log.debug("Processing face recognition event: student={} {}, classroom={}",
                dto.getStudentName(), dto.getStudentLastName(), dto.getClassroomName());
        try {
            ResolvedEvent resolved = resolve(dto);
            FaceRecognitionEvent event = FaceRecognitionEvent.builder()
                    .studentLmsId(resolved.student().getLmsId())
                    .lessonLmsId(resolved.lesson().getLmsId())
                    .recognizedAt(dto.getRecognizedAt())
                    .confidence(dto.getConfidence())
                    .status(FaceRecognitionStatus.PROCESSED)
                    .attendance(resolved.attendance())
                    .build();
            faceRecognitionEventService.save(event);
            log.debug("Saved face recognition event for student={} lesson={}",
                    resolved.student().getLmsId(), resolved.lesson().getLmsId());
        } catch (Exception e) {
            log.warn("Processing failed, saving as FAILED: {}", e.getMessage());
            saveFailedEvent(dto);
        }
    }

    @Override
    @Transactional
    public void retryFailedEvents() {
        LocalDateTime since = LocalDateTime.now().minusHours(1);
        List<FaceRecognitionEvent> failedEvents = faceRecognitionEventService
                .findAllByStatusAndCreatedAfter(FaceRecognitionStatus.FAILED, since);

        log.info("Retrying {} failed events from the last hour", failedEvents.size());

        for (FaceRecognitionEvent event : failedEvents) {
            if (event.getRawPayload() == null) {
                continue;
            }
            try {
                FaceRecognitionEventDto dto = objectMapper.readValue(
                        event.getRawPayload(), FaceRecognitionEventDto.class);
                ResolvedEvent resolved = resolve(dto);
                event.setStudentLmsId(resolved.student().getLmsId());
                event.setLessonLmsId(resolved.lesson().getLmsId());
                event.setAttendance(resolved.attendance());
                event.setStatus(FaceRecognitionStatus.PROCESSED);
                event.setUpdatedAt(LocalDateTime.now());
                faceRecognitionEventService.save(event);
                log.debug("Retry succeeded for event id={}", event.getId());
            } catch (JsonProcessingException e) {
                log.error("Failed to deserialize rawPayload for event id={}", event.getId(), e);
            } catch (Exception e) {
                log.warn("Retry still failing for event id={}: {}", event.getId(), e.getMessage());
            }
        }
    }

    private ResolvedEvent resolve(FaceRecognitionEventDto dto) {
        Classroom classroom = classroomService.findByName(dto.getClassroomName())
                .orElseThrow(() -> new IllegalStateException("Classroom not found: " + dto.getClassroomName()));

        Lesson lesson = lessonRepository.findActiveByClassroomLmsIdAndMoment(
                        classroom.getLmsId(), dto.getRecognizedAt())
                .orElseThrow(() -> new IllegalStateException(
                        "No active lesson for classroom=" + dto.getClassroomName() + " at " + dto.getRecognizedAt()));

        Student student = studentService.findByNameAndLastName(dto.getStudentName(), dto.getStudentLastName())
                .orElseThrow(() -> new IllegalStateException(
                        "Student not found: " + dto.getStudentName() + " " + dto.getStudentLastName()));

        Attendance attendance = attendanceService.findOrCreate(student.getId(), lesson.getId());
        return new ResolvedEvent(student, lesson, attendance);
    }

    private record ResolvedEvent(Student student, Lesson lesson, Attendance attendance) {}

    private void saveFailedEvent(FaceRecognitionEventDto dto) {
        String rawPayload = null;
        try {
            rawPayload = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize dto to rawPayload", e);
        }
        FaceRecognitionEvent event = FaceRecognitionEvent.builder()
                .recognizedAt(dto.getRecognizedAt())
                .confidence(dto.getConfidence())
                .status(FaceRecognitionStatus.FAILED)
                .rawPayload(rawPayload)
                .build();
        faceRecognitionEventService.save(event);
    }
}
