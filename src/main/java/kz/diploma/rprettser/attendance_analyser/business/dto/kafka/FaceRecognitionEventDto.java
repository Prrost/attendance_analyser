package kz.diploma.rprettser.attendance_analyser.business.dto.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FaceRecognitionEventDto {
    private Long studentLmsId;
    private Long classroomLmsId;
    private LocalDateTime recognizedAt;
    private BigDecimal confidence;
}
