package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.StudentGroup;

import java.util.List;
import java.util.Optional;

public interface StudentGroupService {

    StudentGroup save(StudentGroup entity);

    List<StudentGroup> saveAll(List<StudentGroup> entities);

    Optional<StudentGroup> findByLmsId(Long lmsId);

    List<StudentGroup> findAll();
}
