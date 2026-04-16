package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.FaceRecognitionEvent;
import kz.diploma.rprettser.attendance_analyser.dal.enums.FaceRecognitionStatus;

import java.util.List;

public interface FaceRecognitionEventService {

    FaceRecognitionEvent save(FaceRecognitionEvent event);

    List<FaceRecognitionEvent> findAllByStatus(FaceRecognitionStatus status);

    List<FaceRecognitionEvent> findAllByStudentAndLesson(Long studentLmsId, Long lessonLmsId);
}
