package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LmsStudentGroupShortDto {
    private Long id;
    private String name;
    private Boolean isVirtual;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
