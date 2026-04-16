package kz.diploma.rprettser.attendance_analyser.business.dto.lms;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class LmsStudentDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Set<LmsStudentGroupShortDto> studentGroups;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
