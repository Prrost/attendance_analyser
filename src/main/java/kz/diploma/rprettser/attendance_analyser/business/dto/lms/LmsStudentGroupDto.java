package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class LmsStudentGroupDto {
    private Long id;
    private String name;
    private Boolean isVirtual;
    private Set<LmsStudentShortDto> students;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
