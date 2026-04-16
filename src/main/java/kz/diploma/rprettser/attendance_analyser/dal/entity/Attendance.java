package kz.diploma.rprettser.attendance_analyser.dal.entity;

import jakarta.persistence.*;
import kz.diploma.rprettser.attendance_analyser.dal.enums.AttendanceMark;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "mark")
    @Enumerated(EnumType.STRING)
    private AttendanceMark mark;

    @OneToMany(mappedBy = "attendance", fetch = FetchType.LAZY)
    private List<FaceRecognitionEvent> faceRecognitionEvents = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}