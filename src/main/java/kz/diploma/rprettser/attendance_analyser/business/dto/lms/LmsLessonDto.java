package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LmsLessonDto {
    private Long id;
    private String name;
    private LmsClassroomDto classroom;
    private LmsStudentGroupShortDto studentGroup;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
