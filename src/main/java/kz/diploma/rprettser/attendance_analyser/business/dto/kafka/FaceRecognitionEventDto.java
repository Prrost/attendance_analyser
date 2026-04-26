package kz.diploma.rprettser.attendance_analyser.business.dto.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FaceRecognitionEventDto {
    private String studentName;
    private String studentLastName;
    private String classroomName;
    private LocalDateTime recognizedAt;
    private BigDecimal confidence;
}
