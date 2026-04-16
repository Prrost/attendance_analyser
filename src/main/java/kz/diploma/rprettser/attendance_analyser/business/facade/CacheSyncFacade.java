package kz.diploma.rprettser.attendance_analyser.business.facade;

public interface CacheSyncFacade {

    void syncAll();

    void syncClassrooms();

    void syncStudentGroups();

    void syncStudents();

    void syncLessons();
}
