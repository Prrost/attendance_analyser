package kz.diploma.rprettser.attendance_analyser.dal.repository;

import kz.diploma.rprettser.attendance_analyser.dal.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroup, Long>, JpaSpecificationExecutor<StudentGroup> {

    Optional<StudentGroup> findByLmsId(Long lmsId);

    boolean existsByLmsId(Long lmsId);
}
