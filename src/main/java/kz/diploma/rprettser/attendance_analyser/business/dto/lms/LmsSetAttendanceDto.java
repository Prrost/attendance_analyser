package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LmsSetAttendanceDto {
    private String studentName;
    private String lessonName;
    private AttendanceMark attendanceMark;
}
