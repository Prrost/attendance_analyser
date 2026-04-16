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
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lms_id", nullable = false, unique = true)
    private Long lmsId;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students")
    private Set<StudentGroup> studentGroups = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public void addStudentGroup(StudentGroup studentGroup) {
        studentGroups.add(studentGroup);
        studentGroup.getStudents().add(this);
    }

    public void removeStudentGroup(StudentGroup studentGroup) {
        studentGroups.remove(studentGroup);
        studentGroup.getStudents().remove(this);
    }

    public void removeAllStudentGroups() {
        new HashSet<>(studentGroups).forEach(this::removeStudentGroup);
    }
}
