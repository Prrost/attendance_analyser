package kz.diploma.rprettser.attendance_analyser.dal.entity;

import jakarta.persistence.*;
import kz.diploma.rprettser.attendance_analyser.dal.enums.FaceRecognitionStatus;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "face_recognition_event")
public class FaceRecognitionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "student_lms_id")
    private Long studentLmsId;

    @Column(name = "lesson_lms_id")
    private Long lessonLmsId;

    @Column(name = "recognized_at", nullable = false)
    private LocalDateTime recognizedAt;

    @Column(name = "confidence", precision = 5, scale = 4)
    private BigDecimal confidence;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FaceRecognitionStatus status;

    @Column(name = "raw_payload", columnDefinition = "TEXT")
    private String rawPayload;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    @JsonIgnore
    private Attendance attendance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}