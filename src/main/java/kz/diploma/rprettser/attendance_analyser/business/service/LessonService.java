package kz.diploma.rprettser.attendance_analyser.business.service;

import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    Lesson save(Lesson entity);

    List<Lesson> saveAll(List<Lesson> entities);

    Optional<Lesson> findByLmsId(Long lmsId);

    List<Lesson> findAll();
}
