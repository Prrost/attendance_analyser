package kz.diploma.rprettser.attendance_analyser.dal.repository;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Optional<Student> findByLmsId(Long lmsId);

    boolean existsByLmsId(Long lmsId);
}
