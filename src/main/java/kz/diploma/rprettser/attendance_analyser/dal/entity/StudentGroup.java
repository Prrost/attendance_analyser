package kz.diploma.rprettser.attendance_analyser.dal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_group")
public class StudentGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lms_id", nullable = false, unique = true)
    private Long lmsId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_virtual")
    private Boolean isVirtual = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_student_group",
            joinColumns = @JoinColumn(name = "student_group_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public void addStudent(Student student) {
        students.add(student);
        student.getStudentGroups().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getStudentGroups().remove(this);
    }

    public void removeAllStudents() {
        new HashSet<>(students).forEach(this::removeStudent);
    }
}
