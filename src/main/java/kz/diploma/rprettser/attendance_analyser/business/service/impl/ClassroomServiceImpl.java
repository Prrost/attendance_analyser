package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.ClassroomService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Classroom;
import kz.diploma.rprettser.attendance_analyser.dal.repository.ClassroomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository repository;

    @Override
    public Classroom save(Classroom entity) {
        return repository.save(entity);
    }

    @Override
    public List<Classroom> saveAll(List<Classroom> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<Classroom> findByLmsId(Long lmsId) {
        return repository.findByLmsId(lmsId);
    }

    @Override
    public Optional<Classroom> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Classroom> findAll() {
        return repository.findAll();
    }
}
