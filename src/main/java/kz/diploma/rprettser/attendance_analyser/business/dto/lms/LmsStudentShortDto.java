package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LmsStudentShortDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
