package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Classroom;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {

    Classroom save(Classroom entity);

    List<Classroom> saveAll(List<Classroom> entities);

    Optional<Classroom> findByLmsId(Long lmsId);

    Optional<Classroom> findByName(String name);

    List<Classroom> findAll();
}
