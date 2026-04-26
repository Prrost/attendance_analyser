package kz.diploma.rprettser.attendance_analyser.dal.repository;

import kz.diploma.rprettser.attendance_analyser.dal.entity.FaceRecognitionEvent;
import kz.diploma.rprettser.attendance_analyser.dal.enums.FaceRecognitionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FaceRecognitionEventRepository extends JpaRepository<FaceRecognitionEvent, Long>, JpaSpecificationExecutor<FaceRecognitionEvent> {

    List<FaceRecognitionEvent> findAllByStatus(FaceRecognitionStatus status);

    List<FaceRecognitionEvent> findAllByStudentLmsIdAndLessonLmsId(Long studentLmsId, Long lessonLmsId);

    List<FaceRecognitionEvent> findAllByStatusAndCreatedAtAfter(FaceRecognitionStatus status, LocalDateTime since);
}
