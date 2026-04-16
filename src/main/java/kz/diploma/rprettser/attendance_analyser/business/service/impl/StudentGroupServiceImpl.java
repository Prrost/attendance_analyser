package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.StudentGroupService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.StudentGroup;
import kz.diploma.rprettser.attendance_analyser.dal.repository.StudentGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentGroupServiceImpl implements StudentGroupService {

    private final StudentGroupRepository repository;

    @Override
    public StudentGroup save(StudentGroup entity) {
        return repository.save(entity);
    }

    @Override
    public List<StudentGroup> saveAll(List<StudentGroup> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<StudentGroup> findByLmsId(Long lmsId) {
        return repository.findByLmsId(lmsId);
    }

    @Override
    public List<StudentGroup> findAll() {
        return repository.findAll();
    }
}
