package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.FaceRecognitionEventService;
import java.time.LocalDateTime;
import java.util.List;
import kz.diploma.rprettser.attendance_analyser.dal.entity.FaceRecognitionEvent;
import kz.diploma.rprettser.attendance_analyser.dal.enums.FaceRecognitionStatus;
import kz.diploma.rprettser.attendance_analyser.dal.repository.FaceRecognitionEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaceRecognitionEventServiceImpl implements FaceRecognitionEventService {

    private final FaceRecognitionEventRepository repository;

    @Override
    public FaceRecognitionEvent save(FaceRecognitionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        if (event.getCreatedAt() == null) {
            event.setCreatedAt(now);
        }
        event.setUpdatedAt(now);
        return repository.save(event);
    }

    @Override
    public List<FaceRecognitionEvent> findAllByStatus(FaceRecognitionStatus status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public List<FaceRecognitionEvent> findAllByStudentAndLesson(Long studentLmsId, Long lessonLmsId) {
        return repository.findAllByStudentLmsIdAndLessonLmsId(studentLmsId, lessonLmsId);
    }

    @Override
    public List<FaceRecognitionEvent> findAllByStatusAndCreatedAfter(FaceRecognitionStatus status, LocalDateTime since) {
        return repository.findAllByStatusAndCreatedAtAfter(status, since);
    }
}
