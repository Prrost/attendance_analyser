package kz.diploma.rprettser.attendance_analyser.dal.repository;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {

    Optional<Attendance> findByStudentIdAndLessonIdAndIsDeletedFalse(Long studentId, Long lessonId);

    boolean existsByStudentIdAndLessonIdAndIsDeletedFalse(Long studentId, Long lessonId);
}
