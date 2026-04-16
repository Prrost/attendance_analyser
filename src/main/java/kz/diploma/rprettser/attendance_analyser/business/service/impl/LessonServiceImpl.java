package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.LessonService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Lesson;
import kz.diploma.rprettser.attendance_analyser.dal.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository repository;

    @Override
    public Lesson save(Lesson entity) {
        return repository.save(entity);
    }

    @Override
    public List<Lesson> saveAll(List<Lesson> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<Lesson> findByLmsId(Long lmsId) {
        return repository.findByLmsId(lmsId);
    }

    @Override
    public List<Lesson> findAll() {
        return repository.findAll();
    }
}
