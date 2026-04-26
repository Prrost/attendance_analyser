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
import java.util.Optional;

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

        Optional<Classroom> classroomOpt = classroomService.findByName(dto.getClassroomName());
        if (classroomOpt.isEmpty()) {
            log.warn("Classroom not found: {}", dto.getClassroomName());
            saveFailedEvent(dto);
            return;
        }

        Classroom classroom = classroomOpt.get();
        Optional<Lesson> lessonOpt = lessonRepository.findActiveByClassroomLmsIdAndMoment(
                classroom.getLmsId(), dto.getRecognizedAt());
        if (lessonOpt.isEmpty()) {
            log.warn("No active lesson found for classroom={} at {}", dto.getClassroomName(), dto.getRecognizedAt());
            saveFailedEvent(dto);
            return;
        }

        Optional<Student> studentOpt = studentService.findByNameAndLastName(
                dto.getStudentName(), dto.getStudentLastName());
        if (studentOpt.isEmpty()) {
            log.warn("Student not found: {} {}", dto.getStudentName(), dto.getStudentLastName());
            saveFailedEvent(dto);
            return;
        }

        Lesson lesson = lessonOpt.get();
        Student student = studentOpt.get();

        Attendance attendance = attendanceService.findOrCreate(student.getId(), lesson.getId());

        FaceRecognitionEvent event = FaceRecognitionEvent.builder()
                .studentLmsId(student.getLmsId())
                .lessonLmsId(lesson.getLmsId())
                .recognizedAt(dto.getRecognizedAt())
                .confidence(dto.getConfidence())
                .status(FaceRecognitionStatus.PROCESSED)
                .attendance(attendance)
                .build();

        faceRecognitionEventService.save(event);
        log.debug("Saved face recognition event for student={} lesson={}", student.getLmsId(), lesson.getLmsId());
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
                processEvent(dto);
                event.setStatus(FaceRecognitionStatus.PROCESSED);
                event.setUpdatedAt(LocalDateTime.now());
                faceRecognitionEventService.save(event);
            } catch (JsonProcessingException e) {
                log.error("Failed to deserialize rawPayload for event id={}", event.getId(), e);
            } catch (Exception e) {
                log.error("Failed to retry event id={}", event.getId(), e);
            }
        }
    }

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
