package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.FaceRecognitionEventService;
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
        // TODO
        return null;
    }

    @Override
    public List<FaceRecognitionEvent> findAllByStatus(FaceRecognitionStatus status) {
        // TODO
        return List.of();
    }

    @Override
    public List<FaceRecognitionEvent> findAllByStudentAndLesson(Long studentLmsId, Long lessonLmsId) {
        // TODO
        return List.of();
    }
}
