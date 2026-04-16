package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LmsClassroomDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
