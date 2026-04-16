package kz.diploma.rprettser.attendance_analyser.business.facade.impl;

import kz.diploma.rprettser.attendance_analyser.business.client.LmsClient;
import kz.diploma.rprettser.attendance_analyser.business.dto.lms.*;
import kz.diploma.rprettser.attendance_analyser.business.facade.CacheSyncFacade;
import kz.diploma.rprettser.attendance_analyser.business.service.*;
import kz.diploma.rprettser.attendance_analyser.dal.entity.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheSyncFacadeImpl implements CacheSyncFacade {

    private final LmsClient lmsClient;
    private final ClassroomService classroomService;
    private final StudentGroupService studentGroupService;
    private final StudentService studentService;
    private final LessonService lessonService;

    @Override
    @Transactional
    public void syncAll() {
        log.info("Starting full cache sync with LMS");
        syncClassrooms();
        syncStudentGroups();
        syncStudents();
        syncLessons();
        log.info("Full cache sync completed");
    }

    @Override
    @Transactional
    public void syncClassrooms() {
        log.info("Syncing classrooms from LMS");
        List<LmsClassroomDto> dtos = lmsClient.getAllClassrooms();

        List<Classroom> entities = dtos.stream()
            .filter(dto -> !Boolean.TRUE.equals(dto.getIsDeleted()))
            .map(dto -> {
                Classroom classroom = classroomService.findByLmsId(dto.getId())
                    .orElse(new Classroom());
                classroom.setLmsId(dto.getId());
                classroom.setName(dto.getName());
                classroom.setCreatedAt(dto.getCreatedAt());
                classroom.setUpdatedAt(dto.getUpdatedAt());
                classroom.setIsDeleted(false);
                return classroom;
            })
            .toList();

        classroomService.saveAll(entities);
        log.info("Synced {} classrooms", entities.size());
    }

    @Override
    @Transactional
    public void syncStudentGroups() {
        log.info("Syncing student groups from LMS");
        List<LmsStudentGroupDto> dtos = lmsClient.getAllStudentGroups();

        for (LmsStudentGroupDto dto : dtos) {
            if (Boolean.TRUE.equals(dto.getIsDeleted())) continue;

            StudentGroup group = studentGroupService.findByLmsId(dto.getId())
                .orElse(new StudentGroup());
            group.setLmsId(dto.getId());
            group.setName(dto.getName());
            group.setIsVirtual(dto.getIsVirtual());
            group.setCreatedAt(dto.getCreatedAt());
            group.setUpdatedAt(dto.getUpdatedAt());
            group.setIsDeleted(false);
            studentGroupService.save(group);
        }

        log.info("Synced {} student groups", dtos.size());
    }

    @Override
    @Transactional
    public void syncStudents() {
        log.info("Syncing students from LMS");
        List<LmsStudentDto> dtos = lmsClient.getAllStudents();

        for (LmsStudentDto dto : dtos) {
            if (Boolean.TRUE.equals(dto.getIsDeleted())) continue;

            Student student = studentService.findByLmsId(dto.getId())
                .orElse(new Student());
            student.setLmsId(dto.getId());
            student.setName(dto.getName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            student.setPhone(dto.getPhone());
            student.setCreatedAt(dto.getCreatedAt());
            student.setUpdatedAt(dto.getUpdatedAt());
            student.setIsDeleted(false);

            Student savedStudent = studentService.save(student);

            if (dto.getStudentGroups() != null) {
                savedStudent.removeAllStudentGroups();
                for (LmsStudentGroupShortDto groupDto : dto.getStudentGroups()) {
                    studentGroupService.findByLmsId(groupDto.getId())
                        .ifPresent(savedStudent::addStudentGroup);
                }
            }

            studentService.save(savedStudent);
        }

        log.info("Synced {} students", dtos.size());
    }

    @Override
    @Transactional
    public void syncLessons() {
        log.info("Syncing lessons from LMS");
        List<LmsLessonDto> dtos = lmsClient.getAllLessons();

        for (LmsLessonDto dto : dtos) {
            if (Boolean.TRUE.equals(dto.getIsDeleted())) continue;

            Lesson lesson = lessonService.findByLmsId(dto.getId())
                .orElse(new Lesson());
            lesson.setLmsId(dto.getId());
            lesson.setName(dto.getName());
            lesson.setStartsAt(dto.getStartsAt());
            lesson.setEndsAt(dto.getEndsAt());
            lesson.setExpiresAt(dto.getExpiresAt());
            lesson.setCreatedAt(dto.getCreatedAt());
            lesson.setUpdatedAt(dto.getUpdatedAt());
            lesson.setIsDeleted(false);

            if (dto.getClassroom() != null) {
                classroomService.findByLmsId(dto.getClassroom().getId())
                    .ifPresent(lesson::setClassroom);
            }

            if (dto.getStudentGroup() != null) {
                studentGroupService.findByLmsId(dto.getStudentGroup().getId())
                    .ifPresent(lesson::setStudentGroup);
            }

            lessonService.save(lesson);
        }

        log.info("Synced {} lessons", dtos.size());
    }
}
