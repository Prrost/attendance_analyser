package kz.diploma.rprettser.attendance_analyser.dal.repository;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {

    Optional<Lesson> findByLmsId(Long lmsId);

    boolean existsByLmsId(Long lmsId);

    @Query("""
            SELECT l FROM Lesson l
            WHERE l.classroom.lmsId = :classroomLmsId
              AND l.startsAt <= :moment
              AND l.endsAt >= :moment
              AND l.isDeleted = false
            ORDER BY l.startsAt DESC
            """)
    Optional<Lesson> findActiveByClassroomLmsIdAndMoment(
            @Param("classroomLmsId") Long classroomLmsId,
            @Param("moment") LocalDateTime moment
    );

    @Query("""
            SELECT l FROM Lesson l
            WHERE l.startsAt <= :moment
              AND l.endsAt >= :moment
              AND l.isDeleted = false
            """)
    List<Lesson> findAllActiveAtMoment(@Param("moment") LocalDateTime moment);

    @Query("""
            SELECT DISTINCT l FROM Lesson l
            JOIN l.studentGroup sg
            JOIN sg.students s
            WHERE l.endsAt < :now
              AND l.isDeleted = false
              AND NOT EXISTS (
                  SELECT a FROM Attendance a
                  WHERE a.lesson = l
                    AND a.student = s
                    AND a.isDeleted = false
              )
            """)
    List<Lesson> findExpiredLessonsWithMissingAttendance(@Param("now") LocalDateTime now);

    @Query("""
            SELECT DISTINCT l FROM Lesson l
            JOIN l.studentGroup sg
            JOIN sg.students s
            WHERE l.endsAt <= :now
              AND l.isDeleted = false
              AND (
                  EXISTS (
                      SELECT a FROM Attendance a
                      WHERE a.lesson = l
                        AND a.mark IS NULL
                        AND a.isDeleted = false
                  )
                  OR NOT EXISTS (
                      SELECT a FROM Attendance a
                      WHERE a.lesson = l
                        AND a.student = s
                        AND a.isDeleted = false
                  )
              )
            """)
    List<Lesson> findLessonsToFinalize(@Param("now") LocalDateTime now);

    @Query("""
            SELECT DISTINCT l FROM Lesson l
            WHERE l.startsAt <= :now
              AND l.endsAt > :now
              AND l.isDeleted = false
            """)
    List<Lesson> findInProgressLessons(@Param("now") LocalDateTime now);
}
