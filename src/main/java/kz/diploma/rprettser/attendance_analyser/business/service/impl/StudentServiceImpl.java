package kz.diploma.rprettser.attendance_analyser.business.service.impl;

import kz.diploma.rprettser.attendance_analyser.business.service.StudentService;
import kz.diploma.rprettser.attendance_analyser.dal.entity.Student;
import kz.diploma.rprettser.attendance_analyser.dal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Student save(Student entity) {
        return repository.save(entity);
    }

    @Override
    public List<Student> saveAll(List<Student> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public Optional<Student> findByLmsId(Long lmsId) {
        return repository.findByLmsId(lmsId);
    }

    @Override
    public Optional<Student> findByNameAndLastName(String name, String lastName) {
        return repository.findByNameAndLastName(name, lastName);
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }
}
