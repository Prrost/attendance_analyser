package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student save(Student entity);

    List<Student> saveAll(List<Student> entities);

    Optional<Student> findByLmsId(Long lmsId);

    Optional<Student> findByNameAndLastName(String name, String lastName);

    List<Student> findAll();
}
