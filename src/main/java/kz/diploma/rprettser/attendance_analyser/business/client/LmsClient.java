package kz.diploma.rprettser.attendance_analyser.business.client;

import kz.diploma.rprettser.attendance_analyser.business.dto.lms.*;

import java.util.List;

public interface LmsClient {

    List<LmsClassroomDto> getAllClassrooms();

    List<LmsStudentGroupDto> getAllStudentGroups();

    List<LmsStudentDto> getAllStudents();

    List<LmsLessonDto> getAllLessons();

    void setAttendance(LmsSetAttendanceDto dto);
}
